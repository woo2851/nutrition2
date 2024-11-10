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

    @GetMapping("/daily/{id}/{goal}")
    public ArrayList<ArrayList> getNutritionDailyAll(@PathVariable String id, @PathVariable String goal) {
        return this.userService.getNutritionDaily(id, goal);
    }

    @GetMapping("/all/{id}")
    public ArrayList<ArrayList> getNutritionAll(@PathVariable String id) {
        return this.userService.getNutritionAll(id);
    }

    @GetMapping("/all/intake/{id}")
    public ArrayList<String> getNutritionAllIntake(@PathVariable String id) {
        return this.userService.getNutritionAllIntake(id);
    }

    @GetMapping("/recommend/{id}")
    public ArrayList<String> getRecommend(@PathVariable String id) {
        return this.userService.getRecommend(id);
    }

    @GetMapping("/search/{food}")
    public ArrayList<String> getFood(@PathVariable String food) {
        return this.userService.getFood(food);
    }

    @GetMapping("/search/{food}/{kcal}")
    public ArrayList<String> getFood(@PathVariable String food, @PathVariable float kcal) {
        return this.userService.getFood(food, kcal);
    }

    @GetMapping("/add/{id}/{food}")
    public boolean addFood(@PathVariable String id, @PathVariable String food) {
        return this.userService.addFood(id, food);
    }
}
