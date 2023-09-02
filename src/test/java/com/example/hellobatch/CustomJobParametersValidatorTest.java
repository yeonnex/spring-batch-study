package com.example.hellobatch;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CustomJobParametersValidatorTest {

    @Test
    void switchCaseTest() {
        String today = LocalDate.now().getDayOfWeek().name();
        String result = switch (today) {
            case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Week day";
            case "Saturday", "Sunday" -> "Weekend";
            default -> "Unknown";
        };
        System.out.printf("Today is %s%n", result);
    }

}