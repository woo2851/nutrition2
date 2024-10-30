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
        return this.userService.getNutrition(id, nutrition);
    }

    @GetMapping("/daily/{id}")
    public ArrayList<ArrayList> getNutritionDailyAll(@PathVariable String id) {
        System.out.println("daily");
        return this.userService.getNutritionDaily(id);
    }

    @GetMapping("/all/{id}")
    public ArrayList<ArrayList> getNutritionAll(@PathVariable String id) {
        return this.userService.getNutritionAll(id);
    }
}
