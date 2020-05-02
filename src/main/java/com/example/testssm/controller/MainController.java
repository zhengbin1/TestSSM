package com.example.testssm.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@Controller
public class MainController {

    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String index(Model model){

        String userName = new String();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                System.out.println("当前用户 " + userName + " 权限 " + grantedAuthority.getAuthority());
            }
        } else {
            userName = "无法获取用户";
        }

        model.addAttribute("username", userName);

        return "index";
    }

    @RestController
    public class DruidStatController {
        @GetMapping("/druid/stat")
        public Object druidStat(){
            // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
            return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
        }
    }
}
