package com.example.nutrition.web;

import com.example.nutrition.domain.User;
import com.example.nutrition.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("signup")
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {this.userService = userService;}

}
