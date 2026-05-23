package com.angela.stardewvalley.controller;

import com.angela.stardewvalley.user.req.LoginReq;
import com.angela.stardewvalley.user.req.RegisterReq;
import com.angela.stardewvalley.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /*
    登录
     */
    @PostMapping("/login")
    public long login(@RequestBody LoginReq req){
        return userService.login(req.getUsername(),req.getPassword());
    }

    /*
    注册
     */
    @PostMapping("/register")
    public boolean register(@RequestBody RegisterReq req){
        return userService.register(req.getUsername(), req.getPassword());
    }
}
