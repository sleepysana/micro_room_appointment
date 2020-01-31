package cn.akira.controller;

import cn.akira.pojo.ResponseData;
import cn.akira.util.ValidateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

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
    public String init(HttpServletRequest request) {
        Object session_user = request.getSession().getAttribute("SESSION_USER");
        if (session_user == null) {
            return "login";
        }
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
}