package com.example.nutrition.domain.batch;

import com.example.nutrition.domain.entity.Nutrition;
import com.example.nutrition.domain.repository.NutritionRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelItemWriter implements ItemWriter<Nutrition> {

    @Autowired
    private NutritionRepository repository;

    @Override
    public void write(Chunk<? extends Nutrition> items) {
        repository.saveAll(items);
    }

}