package com.example.hellobatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

import java.util.Objects;

public class CustomJobParametersValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if (Objects.requireNonNull(parameters).getString("name") == null) {
            throw new JobParametersInvalidException("name parameter is not fount...");
        }
    }
}
