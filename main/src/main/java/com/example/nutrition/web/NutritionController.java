package com.example.nutrition.web;

import com.example.nutrition.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/getNutrition")
public class NutritionController {
    private final UserService userService;

    @GetMapping("/{id}/{nutrition}")
    public ArrayList<ArrayList> getNutrition(@PathVariable String id, @PathVariable String nutrition) {
        System.out.println(nutrition);
        return this.userService.getNutrition(id, nutrition);
    }
}
