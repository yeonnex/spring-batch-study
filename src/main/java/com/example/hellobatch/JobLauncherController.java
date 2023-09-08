package com.example.hellobatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;

//@RestController
@RequiredArgsConstructor
public class JobLauncherController {
    private final Job job;
    private final BasicBatchConfigurer basicBatchConfigurer;

//    @PostMapping("/batch")
//    public String launch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException,
//            JobExecutionAlreadyRunningException,
//            JobParametersInvalidException,
//            JobRestartException {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("id", member.getId())
//                .addDate("date", new Date())
//                .toJobParameters();
//        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
//        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        jobLauncher.run(job, jobParameters);
//        return "batch completed.";
//    }
}
