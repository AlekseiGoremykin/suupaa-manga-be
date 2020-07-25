package com.suupaa.manga.manga.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.suupaa.manga.manga.entity.Chapter;

import reactor.core.publisher.Flux;

@Repository
public interface ChapterRepository extends ReactiveMongoRepository<Chapter, Long> {

    Flux<Chapter> findByMangaIdOrderByName(String mangaId);
}
