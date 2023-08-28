package com.example.hellobatch;

import com.example.hellobatch.tasklet.Tasklet1;
import com.example.hellobatch.tasklet.Tasklet2;
import com.example.hellobatch.tasklet.Tasklet3;
import com.example.hellobatch.tasklet.Tasklet4;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final Tasklet1 tasklet1;
    private final Tasklet2 tasklet2;
    private final Tasklet3 tasklet3;
    private final Tasklet4 tasklet4;

    @Bean
    Job job() {
        return jobBuilderFactory.get("hiJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet1)
                .build();
    }

    @Bean
    Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(tasklet2)
                .build();
    }

    @Bean
    Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(tasklet3)
                .build();
    }

    @Bean
    Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(tasklet4)
                .build();
    }

}
