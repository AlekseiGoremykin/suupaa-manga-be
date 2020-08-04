package com.suupaa.manga.manga.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.suupaa.manga.manga.entity.Rate;

@Repository
public interface RateRepository extends ReactiveMongoRepository<Rate, Rate.RateKey> {

}
