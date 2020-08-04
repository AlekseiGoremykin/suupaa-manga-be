package com.suupaa.manga.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class RecommendationApplication implements WebFluxConfigurer {


    public static void main(String[] args) {
        SpringApplication.run(RecommendationApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .maxAge(3600);
    }


}
