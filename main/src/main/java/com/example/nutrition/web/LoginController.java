package com.example.nutrition.web;

import com.example.nutrition.domain.JoinRequest;
import com.example.nutrition.domain.LoginRequest;
import com.example.nutrition.domain.User;
import com.example.nutrition.domain.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/login/{id}")
    public String login(@RequestBody LoginRequest req) {
        return this.userService.login(req);
    }

    @PostMapping("/signup/{id}")
    public void signup(@Valid @RequestBody JoinRequest req) {
        this.userService.join(req);
    }
}
