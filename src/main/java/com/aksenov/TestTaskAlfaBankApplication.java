package com.aksenov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestTaskAlfaBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestTaskAlfaBankApplication.class, args);
    }
}
