package com.example.hellobatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Job job() {
        return jobBuilderFactory.get("helloJob")
                .start(helloJobStep1())
                .next(helloJobStep2())
                .build();
    }

    @Bean
    Step helloJobStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println(" >> 안녕, 스프링 배치 step 1");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    Step helloJobStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println(" >> 안녕, 스프링 배치 step 2");
                    System.out.println("===================");
                    throw new RuntimeException("step2 가 실패하였습니다...");
//                    return RepeatStatus.FINISHED;
                }).build();
    }
}
