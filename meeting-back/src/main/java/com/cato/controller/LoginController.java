package com.cato.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/26
 * @Description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 返回用户角色
     * @return
     */
    @RequestMapping("/getRole")
    public Collection<? extends GrantedAuthority> getRole(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAuthorities();
    }

    @RequestMapping("/logError")
    public String logError(){
        return "用户名或密码错误!";
    }
}
