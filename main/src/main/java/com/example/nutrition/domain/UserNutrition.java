package com.example.nutrition.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserNutrition")
public class UserNutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Float kcal;
    private Float carb;
    private Float sugar;
    private Float Fat;
    private Float protein;
    private String imageurl;
    private LocalDate date;
}
