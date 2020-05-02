package com.example.testssm.controller;

import com.example.testssm.dao.LoginUser;
import com.example.testssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/form", method = {RequestMethod.GET, RequestMethod.POST})
    public String form() {

        return "redirect:/index";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }


    @PostMapping("/regusernameajax")
    @ResponseBody
    public String is_exist_name(String username) {

        LoginUser loginUser = userService.getUserByName(username);

        if(loginUser == null) {
            return "false";
        }

        return "true";
    }

    @PostMapping("/register_save")
    public String registerSave(String username, String password2){
        int ret = 0;

        if(username.equals("admin")){
            ret = userService.insertUserBasic("admin", password2, "admin");
        }else{
            ret = userService.insertUserBasic(username, password2, "user");
        }

        if(ret <= 0){
            return "register_error";
        } else {
            return "redirect:/login";
        }
    }
}