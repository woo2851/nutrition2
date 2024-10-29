package com.example.nutrition.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserNutritionRepository extends JpaRepository<UserNutrition, Long> {
    List<UserNutrition> findByUserIdAndDate(String userId, LocalDate date);
}
