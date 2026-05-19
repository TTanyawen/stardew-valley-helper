package com.angela.stardewvalley.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    public String test() {
        return "hello world";
    }
}
