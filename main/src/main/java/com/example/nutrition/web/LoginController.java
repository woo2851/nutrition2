package com.example.nutrition.web;

import com.example.nutrition.domain.User;
import com.example.nutrition.domain.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {this.userService = userService;}

    @PostMapping("{id}")
    public Optional<User> getbyId(@PathVariable String id) {
        return userService.getUser(id);
    }
}
