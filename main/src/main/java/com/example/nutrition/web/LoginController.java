package com.example.nutrition.web;

import com.example.nutrition.domain.dto.JoinRequest;
import com.example.nutrition.domain.dto.LoginRequest;
import com.example.nutrition.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/login/{id}")
    public String login(@RequestBody LoginRequest req) {
        return this.userService.login(req);
    }

    @PostMapping("/signup/{id}")
    public String signup(@Valid @RequestBody JoinRequest req) {
        return this.userService.join(req);
    }
}
