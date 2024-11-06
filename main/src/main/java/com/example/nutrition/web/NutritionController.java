package com.example.nutrition.web;

import com.example.nutrition.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

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
        return this.userService.getNutritionDaily(id);
    }

    @GetMapping("/all/{id}")
    public ArrayList<ArrayList> getNutritionAll(@PathVariable String id) {
        return this.userService.getNutritionAll(id);
    }

    @GetMapping("/recommend/{id}")
    public ArrayList<String> getRecommend(@PathVariable String id) {
        return this.userService.getRecommend(id);
    }

    @GetMapping("/search/{food}")
    public ArrayList<String> getFood(@PathVariable String food) {
        return this.userService.getFood(food);
    }

    @GetMapping("/add/{id}/{food}")
    public boolean addFood(@PathVariable String id, @PathVariable String food) {
        return this.userService.addFood(id, food);
    }
}
