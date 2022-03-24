package com.example.coinprofit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorsConfig {
    public @Bean ExecutorService coinPriceExecutors() {
        return Executors.newFixedThreadPool(10);
    }

    public @Bean ExecutorService fileDownloadExecutors() {
        return Executors.newFixedThreadPool(2);
    }
}
