package com.suupaa.manga.manga.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.suupaa.manga.manga.entity.Manga;

@Repository
public interface MangaRepository extends ReactiveMongoRepository<Manga, String> {
}
