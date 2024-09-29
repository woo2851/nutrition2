package com.example.nutrition.domain;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public Optional<User> getUser(String id) {
        System.out.println("호출");
        return userRepository.findByUserId(id);
    }
}
