package com.example.nutrition.web;

import com.example.nutrition.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class ImageController {

    private final UserService userService;

    @PostMapping("/{id}/{foodName}")
    public boolean uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String id, @PathVariable String foodName) throws IOException, InterruptedException {
        return this.userService.uploadImage(file, id, foodName);
    }
}
