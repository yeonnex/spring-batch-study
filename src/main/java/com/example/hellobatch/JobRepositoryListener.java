package com.example.hellobatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobRepositoryListener implements JobExecutionListener {
    private final JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestData", "20210102").toJobParameters();

        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if (lastJobExecution != null) {
            for (StepExecution stepExecution : lastJobExecution.getStepExecutions()) {
                BatchStatus status = stepExecution.getStatus();
                ExitStatus exitStatus = jobExecution.getExitStatus();
                log.info("stepName: {}, status: {}, exitStatus: {}", stepExecution.getStepName(), status, exitStatus);
            }
        }
    }
}
