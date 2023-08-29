package com.example.hellobatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
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
    private final JobExecutionListener jobExecutionListener;

    @Bean
    Job job() {
        return jobBuilderFactory.get("hiJob")
                .start(step1())
                .next(step2())
                .listener(jobExecutionListener)
                .build();
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

}
