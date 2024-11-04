package com.example.nutrition.domain.repository;

import com.example.nutrition.domain.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    @Query(value = "SELECT * FROM NUTRITION ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Nutrition> findRandomRecommend();
}
