package com.msm.service.expense.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.msm.service.expense")
public class ExpenseSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseSchedulerApplication.class, args);
    }

}
