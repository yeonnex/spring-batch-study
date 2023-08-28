package com.example.hellobatch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Tasklet1 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Step 1 was executed...");
        // 잡 ExecutionContext 추출
        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        // 스텝 ExecutionContext 추출
        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

        // 잡 이름 추출
        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
        // 스텝 이름 추출
        String stepName = chunkContext.getStepContext().getStepName();

        if (jobExecutionContext.get("jobName") == null) {
            jobExecutionContext.put("jobName", jobName);
        }

        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName);
        }
        log.info("jobName: {}", jobExecutionContext.get("jobName"));
        log.info("stepName: {}", stepExecutionContext.get("stepName"));
        return RepeatStatus.FINISHED;
    }
}
