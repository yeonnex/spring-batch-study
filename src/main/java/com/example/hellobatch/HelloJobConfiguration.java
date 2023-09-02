package com.example.hellobatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
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

    /**
     * 심플 잡을 생성 한다.
     *
     * @return 심플 잡
     */
    @Bean
    Job job() {
        return jobBuilderFactory.get("hiJob")
                .start(step1())
                .next(step2())
                //.validator(new CustomJobParametersValidator())
                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"},
                                                                                new String[]{"count"}))
                .build();
    }

    /**
     * 플로우 잡을 생성 한다. 플로우 잡을 마칠 떄에는 {@code .end()} 를 반드시 호출해야 한다.
     *
     * @return 플로우 잡
     */
//    @Bean
    Job batchJob2() {
        return jobBuilderFactory.get("batchJob2")
                .start(flow())
                .next(step5())
                .end().build();
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        Thread.sleep(3000);
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        return flowBuilder
                .start(step3())
                .next(step4())
                .end();
    }

    @Bean
    Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 was executed...");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step4 was executed...");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step5 was executed...");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
