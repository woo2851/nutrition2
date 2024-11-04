package com.example.nutrition.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NutritionSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String main_food;
    private Float kcal;
    private Float protein;
    private Float fat;
    private Float carb;
    private Float sugar;
}
