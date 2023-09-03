package com.example.hellobatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    Job hiJob() {
        return jobBuilderFactory.get("hiJob")
                .start(step1())
                .next(step2())
                //.validator(new CustomJobParametersValidator())
                //.validator(new DefaultJobParametersValidator(new String[]{"name", "date"},
                //                                             new String[]{"count"}))
                // .incrementer(new CustomJobParametersIncrementer())
                .incrementer(new RunIdIncrementer())
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
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    Flow helloFlow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("helloFlow");
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


    /**
     * 태스크릿 스텝을 생성한다
     *
     * @return 태스트릿스텝
     */
    @Bean
    Step step6() {
        return stepBuilderFactory.get("step6")
                .tasklet((contribution, chunkContext) -> null)
                .build();
    }

    /**
     * 청크기반 스텝을 생성한다
     *
     * @return 청크기반 스텝
     */
    @Bean
    Step step7() {
        return stepBuilderFactory.get("step7")
                .<String, String>chunk(3)
                .reader(new ItemReader<String>() {
                    @Override
                    public String read() throws Exception {
                        return null;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return null;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {

                    }
                })
                .build();
    }

    /**
     * 멀티스레드 방식으로 잡을 실행하는 파티션스텝을 생성한다
     *
     * @return 파티션 스텝
     */
    @Bean
    Step step8() {
        return stepBuilderFactory.get("step8")
                .partitioner(step1())
                .gridSize(2)
                .build();
    }

    /**
     * 잡을 품고 있는 스텝을 생성한다.
     *
     * @return 잡 스텝
     */
    @Bean
    Step step9() {
        return stepBuilderFactory.get("step9")
                .job(job())
                .build();
    }

    @Bean
    Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .start(step2())
                .start(step3())
                .build();
    }

    /**
     * 플로우를 실행시키는 스텝을 생성한다
     *
     * @return 플로우 스텝
     */
    @Bean
    Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2()).end();
        return flowBuilder.build();
    }

    /**
     * 태스크 기반 태스크릿 스텝을 생성한다
     *
     * @return 태스크기반 태스크릿 스텝
     */
    @Bean
    Step step10() {
        return stepBuilderFactory.get("step10")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    /**
     * 청크기반 태스크릿 스텝을 생성한다
     *
     * @return 청크기반 태스크릿 스텝
     */
    @Bean
    Step step11() {
        return stepBuilderFactory.get("step11")
                .<String, String>chunk(10)
                .reader(new ListItemReader<>(List.of("item1", "item2", "item3", "item4", "item5")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return item.toUpperCase();
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {
                        items.forEach(item -> System.out.println(item));
                    }
                }).build();
    }

}
