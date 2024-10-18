package com.example.backend.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecretLoggingAppRunner implements ApplicationRunner {

    @Value("${secret.message}")
    private String secretMessage;

    @Override
    public void run(ApplicationArguments args) {
        log.info("The secret message is: {}", secretMessage);
    }
}
