package com.example.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Slf4j
    @Component
    static class AppRunner implements ApplicationRunner {

        @Value("${secret.message}")
        private String secretMessage;

        @Override
        public void run(ApplicationArguments args) {
            log.info("The secret message is: {}", secretMessage);
        }
    }

}
