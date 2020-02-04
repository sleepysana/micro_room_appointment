package cn.akira.controller;

import cn.akira.pojo.ResponseData;
import cn.akira.pojo.User;
import cn.akira.service.UserService;
import cn.akira.util.FtpUtil;
import cn.akira.util.RsaUtil;
import cn.akira.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    //原来是在web.xml中将初始化页面设置为init.jsp, 再在该页面初始化时请求此方法，从而根据会话中是否有用户登录来重定向页面
    //现已将页面初始化方法改为此Controller类中的init方法，遂弃用此方法
/*
    @RequestMapping("/check")
    @ResponseBody
    public ResponseData checkSession(HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        Object session_user = request.getSession().getAttribute("SESSION_USER");
        String contextPath = request.getContextPath();
        if (session_user == null) {
            responseData.setResource(contextPath + "/user/login");
        } else {
            responseData.setResource(contextPath + "/index");
        }
        return responseData;
    }
*/

    @RequestMapping
    public String init(HttpServletRequest request, Model model) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, TransformerException {
        User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
        //如果会话中不存在用户信息则返回登录页面
        if (sessionUser == null) {
            return "login";
        }
        String headIconBase64=FtpUtil.getUserIconBase64(sessionUser);
        model.addAttribute("SESSION_USER", sessionUser);
        model.addAttribute("headIconBase64", headIconBase64);
        return "index";
    }

    @RequestMapping("/register")
    public String toRegisterPage() {
        return "register";
    }

    @RequestMapping("/generateVerifyCode")
    @ResponseBody
    public ResponseData generateVerifyCode() throws IOException {
        ResponseData responseData = new ResponseData();
        Map<String, String> verifyCodeMap = ValidateUtil.generateVerifyCodeImgBase64();
        responseData.setResource(verifyCodeMap);
        return responseData;
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseData doLogin(@RequestParam("credential") String credential,
                                @RequestParam("password") String password,
                                HttpSession session,
                                HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        String phoneReg = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
        String emailReg = "^[a-z0-9A-Z]+[- |a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
        User user = new User();
        if (credential.matches(phoneReg)) {
            user.setLoginPhoneNo(credential);
        } else if (credential.matches(emailReg)) {
            user.setLoginEmail(credential);
        } else {
            user.setLoginName(credential);
        }
        String contextPath = request.getContextPath();
        try {
            User user_db = userService.getUserSessionCols(user);
            if (user_db == null) {
                responseData.setFlag(false);
                responseData.setMessage("未能在系统中找到该用户信息");
                responseData.setResource(contextPath + "login");
                return responseData;
            }
            String rsaPassword = user_db.getRsaPassword();
            String decPassword = RsaUtil.decrypt(rsaPassword);
            if (password.equals(decPassword)) {
                session.removeAttribute("SESSION_USER");
                user.setRsaPassword(RsaUtil.encrypt(password));
                session.setAttribute("SESSION_USER", user_db);
            } else {
                responseData.setFlag(false);
                responseData.setMessage("密码不正确");
            }
            responseData.setResource(contextPath);
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setExceptionInfo(e);
        }
        return responseData;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ResponseData logout(HttpSession session) {
        ResponseData responseData = new ResponseData();
        session.removeAttribute("SESSION_USER");
        session.removeAttribute("headIconBase64");
        return responseData;
    }
}