package com.suupaa.manga.manga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MangaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangaApplication.class, args);
    }
}
