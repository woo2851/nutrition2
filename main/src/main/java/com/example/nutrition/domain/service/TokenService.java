//package com.example.nutrition.domain.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class TokenService {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    public void storeToken(String username, String token) {
//        redisTemplate.opsForValue().set(username, token, 10, TimeUnit.HOURS);
//    }
//
//    public void deleteToken(String username) {
//        redisTemplate.delete(username);
//    }
//
//    public String getToken(String username) {
//        return redisTemplate.opsForValue().get(username);
//    }
//}
