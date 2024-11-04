package com.example.nutrition.domain.repository;

import com.example.nutrition.domain.entity.NutritionSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionSearchRepository extends JpaRepository<NutritionSearch, Long> {
}

