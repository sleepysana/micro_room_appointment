package cn.akira.controller;

import cn.akira.pojo.ResponseData;
import cn.akira.pojo.VerifyInfo;
import cn.akira.util.ValidateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @RequestMapping("/sendVerifyEmail")
    @ResponseBody
    public ResponseData sendVerifyEmail(@RequestParam("loginName") String loginName,
                                        @RequestParam("email") String email) throws GeneralSecurityException, UnsupportedEncodingException, MessagingException {
        ResponseData responseData = new ResponseData();
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        String vrfCode = String.valueOf(i);
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyEmail(email);
        verifyInfo.setVerifyCode(vrfCode);
        String content = "感谢您注册成为本网站用户，【" + vrfCode + "】是您本次邮箱注册的验证码 :)";
        ValidateUtil.sendVerifyEmail(email, loginName + " 敬启：", content);
        return responseData;
    }
}
