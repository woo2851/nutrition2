//package com.example.nutrition.batch;
//
//import com.example.nutrition.domain.entity.Nutrition;
//import com.example.nutrition.domain.repository.NutritionRepository;
//import org.apache.poi.ss.usermodel.Row;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemStreamReader;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.io.IOException;
//
//@Configuration
//public class FourthBatch {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager platformTransactionManager;
//    private final NutritionRepository nutritionRepository;
//
//    public FourthBatch(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, NutritionRepository nutritionRepository) {
//        this.jobRepository = jobRepository;
//        this.platformTransactionManager = platformTransactionManager;
//        this.nutritionRepository = nutritionRepository;
//    }
//
//    @Bean
//    public Job fourthJob() {
//
//        System.out.println("fourth job");
//
//        return new JobBuilder("fourthJob", jobRepository)
//                .start(fourthStep())
//                .build();
//    }
//
//    @Bean
//    public Step fourthStep() {
//
//        return new StepBuilder("fourthStep", jobRepository)
//                .<Row, Nutrition> chunk(10, platformTransactionManager)
//                .reader(excelReader())
//                .processor(fourthProcessor())
//                .writer(fourthAfterWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemStreamReader<Row> excelReader() {
//
//        try {
//            return new ExcelRowReader("C:\\Users\\woo28\\Desktop\\nutrition22\\main\\src\\main\\java\\com\\example\\nutrition\\batch\\data.xlsx");
//            //리눅스나 맥은 /User/형태로
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public ItemProcessor<Row, Nutrition> fourthProcessor() {
//
//        return new ItemProcessor<Row, Nutrition>() {
//
//            @Override
//            public Nutrition process(Row item) {
//
//                Nutrition nutrition = new Nutrition();
//                nutrition.setName(item.getCell(0).getStringCellValue());
//                nutrition.setCategory(item.getCell(1).getStringCellValue());
//                nutrition.setMain_food(item.getCell(2).getStringCellValue());
//                nutrition.setKcal((float) item.getCell(3).getNumericCellValue());
//                nutrition.setProtein((float) item.getCell(4).getNumericCellValue());
//                nutrition.setFat((float) item.getCell(5).getNumericCellValue());
//                nutrition.setCarb((float) item.getCell(6).getNumericCellValue());
//                nutrition.setSugar((float) item.getCell(7).getNumericCellValue());
//                return nutrition;
//            }
//        };
//    }
//
//    @Bean
//    public RepositoryItemWriter<Nutrition> fourthAfterWriter() {
//
//        return new RepositoryItemWriterBuilder<Nutrition>()
//                .repository(nutritionRepository)
//                .methodName("save")
//                .build();
//    }
//}