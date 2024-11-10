//package com.example.nutrition.web;
//
//import com.example.nutrition.auth.JwtUtil;
//import com.example.nutrition.domain.service.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String loginId, @RequestParam String password) {
//        try {
//            String token = jwtUtil.generateToken(loginId);
//            tokenService.storeToken(loginId, token);
//            return ResponseEntity.ok(token);
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestParam String username) {
//        tokenService.deleteToken(username);
//        return ResponseEntity.ok("Logged out successfully");
//    }
//}