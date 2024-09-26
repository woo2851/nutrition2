package com.example.nutrition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.servlet.error.ErrorController;

@RestController
public class HomeController {

    @GetMapping("api/hello")
    public String test() {
        return "Hello World";
    }
}


