package com.example.hellobatch;

import com.example.hellobatch.tasklet.CustomTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
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
                .tasklet(new CustomTasklet()).build();
    }

    @Bean
    Step helloJobStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        contribution.getStepExecution().getJobExecution().getJobInstance().getJobName();
                        System.out.println(" >> 안녕, 스프링 배치 step 2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
