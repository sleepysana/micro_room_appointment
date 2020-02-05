package cn.akira.controller;

import cn.akira.pojo.ResponseData;
import cn.akira.pojo.User;
import cn.akira.pojo.VerifyInfo;
import cn.akira.service.UserService;
import cn.akira.service.VerifyInfoService;
import cn.akira.util.NetUtil;
import cn.akira.util.RsaUtil;
import cn.akira.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private VerifyInfoService verifyCodeService;

    @Autowired
    private UserService userService;

    @RequestMapping("checkRegInfo")
    @ResponseBody
    public ResponseData checkRegInfo(User user) {
        ResponseData responseData = new ResponseData();
        Integer count = userService.queryCountByConditions(user);
        responseData.setResource(count);
        return responseData;
    }

    @RequestMapping("/sendVerifyEmail")
    @ResponseBody
    public ResponseData sendVerifyEmail(@RequestParam("loginName") String loginName,
                                        @RequestParam("email") String email) throws Exception {
        ResponseData responseData = new ResponseData();
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        String vrfCode = String.valueOf(i);
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyEmail(email);
        verifyCodeService.deleteByConditions(verifyInfo);
        verifyInfo.setVerifyType("01");
        verifyInfo.setVerifyCode(vrfCode);
        verifyCodeService.insertVerifyInfo(verifyInfo);
        String content = "感谢您注册成为本站用户，【" + vrfCode + "】是您本次邮箱注册的验证码 :) " +
                "\n如您不知道这封邮件是怎么回事，您大可忽略并删除它。";
        NetUtil.sendEmail(email, loginName + " 敬启：", content);
        return responseData;
    }

    @RequestMapping("/doReg")
    @ResponseBody
    public ResponseData doReg(User user,
                              @RequestParam("verifyCode") String verifyCode,
                              @RequestParam("password") String password) throws Exception {
        ResponseData responseData = new ResponseData();
        VerifyInfo verifyInfo_local = new VerifyInfo();
        if (user.getLoginEmail()!=null && !"".equals(user.getLoginEmail().trim())){
            verifyInfo_local.setVerifyEmail(user.getLoginEmail());
        }else if(user.getLoginPhoneNo()!=null&&!"".equals(user.getLoginPhoneNo().trim())){
            verifyInfo_local.setVerifyPhoneNo(user.getLoginPhoneNo());
        }else{
            responseData.setFlag(false);
            responseData.setMessage("无法获取邮箱地址或电话号码");
            return responseData;
        }
        verifyInfo_local.setVerifyCode(verifyCode);
        verifyInfo_local.setVerifyType("01");
        VerifyInfo verifyInfo_server = verifyCodeService.queryAllByConditions(verifyInfo_local);
        String verifyCode_server = verifyInfo_server.getVerifyCode();
        long nowTime = new Date().getTime();
        long generateTime = verifyInfo_server.getGenerateDate().getTime();
        long detaTime = nowTime - generateTime;
        if (!verifyCode.equals(verifyCode_server)) {
            responseData.setFlag(false);
            responseData.setMessage("验证码不正确");
        } else if (detaTime > 900000) {
            responseData.setFlag(false);
            responseData.setMessage("验证码已过期，请重新发送");
        } else {
            user.setRsaPassword(RsaUtil.encrypt(password));
            userService.insert(user);
        }
        return responseData;
    }
}
