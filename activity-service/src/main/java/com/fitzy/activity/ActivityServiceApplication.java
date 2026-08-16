package com.fitzy.activity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Arrays;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.fitzy")
@EnableDiscoveryClient

public class ActivityServiceApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(ActivityServiceApplication.class, args);    }


}
