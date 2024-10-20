package com.example.nutrition.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserNutritionRepository extends JpaRepository<UserNutrition, Long> {
    List<UserNutrition> findByUserIdAndDate(String userId, LocalDate date);
}
