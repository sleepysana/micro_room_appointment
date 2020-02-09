package cn.akira.controller;

import cn.akira.pojo.ResponseData;
import cn.akira.pojo.User;
import cn.akira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String toLoginPage(){
        return "login";
    }

    @RequestMapping("/baseInfoEdit/{userId}")
    public String toBaseInfoEdit(@PathVariable Integer userId, Model model){
        User conditions  = new User();
        ResponseData result = new ResponseData();
        try {
            conditions.setUserId(userId);
            User user = userService.getUser(conditions);
            model.addAttribute("user",user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setExceptionInfo(e);
            result.setMessage(e.getMessage());
        }
        model.addAttribute("resultData",result);
        return "user/baseInfo";
    };
}
