package com.example.nutrition.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserNutrition")
public class UserNutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String food_name;
    private String userId;
    private Float kcal;
    private Float carb;
    private Float sugar;
    private Float Fat;
    private Float protein;
    private String imageurl;
    private LocalDate date;
}
