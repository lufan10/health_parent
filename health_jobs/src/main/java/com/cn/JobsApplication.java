package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务功能
@SpringBootApplication
public class JobsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobsApplication.class, args);
    }
}
