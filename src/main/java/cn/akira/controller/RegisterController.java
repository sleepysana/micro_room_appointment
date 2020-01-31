package cn.akira.controller;

import cn.akira.pojo.ResponseData;
import cn.akira.pojo.User;
import cn.akira.pojo.VerifyInfo;
import cn.akira.service.UserService;
import cn.akira.service.VerifyInfoService;
import cn.akira.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private VerifyInfoService verifyCodeService;

    @Autowired
    private UserService userService;

    @RequestMapping("checkRegInfo")
    @ResponseBody
    public ResponseData checkRegInfo(User user){
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
        verifyInfo.setVerifyType("01");
        verifyInfo.setVerifyEmail(email);
        verifyInfo.setVerifyCode(vrfCode);
        verifyCodeService.insertVerifyInfo(verifyInfo);
        String content = "感谢您注册成为本站用户，【" + vrfCode + "】是您本次邮箱注册的验证码 :)" +
                "\n如您不知道这封邮件是怎么回事，您大可忽略并删除。";
        ValidateUtil.sendVerifyEmail(email, loginName + " 敬启：", content);
        return responseData;
    }
}
