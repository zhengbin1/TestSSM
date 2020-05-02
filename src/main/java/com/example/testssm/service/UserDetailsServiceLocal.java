package com.example.testssm.service;

import com.example.testssm.dao.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceLocal implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        LoginUser loginUser = userService.getUserByName(username);

        if(loginUser == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ROLE_" + loginUser.getRole()));

        User user = new User(loginUser.getUsername(), passwordEncoder.encode(loginUser.getPassword()), authorities);

        return user;
    }
}
