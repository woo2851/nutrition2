package com.example.nutrition.domain.repository;

import com.example.nutrition.domain.entity.NutritionSearch;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface NutritionSearchRepository extends JpaRepository<NutritionSearch, Long> {
    @Query(value = "SELECT CONCAT(name, '  ',  kcal) FROM nutrition_search where name like %:food%", nativeQuery = true)
    ArrayList<String> getFood(@Param("food") String food);

    NutritionSearch findByName(String food);
}

