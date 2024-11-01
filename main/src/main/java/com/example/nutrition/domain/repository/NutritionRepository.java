package com.example.nutrition.domain.repository;

import com.example.nutrition.domain.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
}
