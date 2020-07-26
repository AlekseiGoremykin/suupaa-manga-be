package com.suupaa.manga.manga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MangaApplication implements WebFluxConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MangaApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .maxAge(3600);
    }
}
