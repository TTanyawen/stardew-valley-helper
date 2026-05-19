package com.angela.stardewvalley.user.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;


    @Test
    void register() {
        System.out.println(userService.register("angela","123456"));
    }

    @Test
    void login() {
        System.out.println(userService.login("angela","123456"));
    }
}