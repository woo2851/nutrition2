package com.example.nutrition.web;

import com.example.nutrition.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class ImageController {

    private final UserService userService;

    @PostMapping("/{id}")
    public void uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String id) throws IOException, InterruptedException {
        this.userService.uploadImage(file, id);
    }
}
