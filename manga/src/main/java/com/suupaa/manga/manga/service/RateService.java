package com.suupaa.manga.manga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.events.RateEvent;
import com.suupaa.manga.manga.entity.Rate;
import com.suupaa.manga.manga.repository.RateRepository;

import reactor.core.publisher.Mono;

@Service
@Transactional
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private KafkaTemplate<String, RateEvent> kafkaTemplate;

    @Value("${kafka.topic.manga-rate}")
    private String mangaRateTopic;

    public Mono<Void> rate(String mangaId, Integer rate) {
        validateRate(rate);

        Rate.RateKey rateKey = new Rate.RateKey();
        rateKey.setUserId("userId");
        rateKey.setMangaId(mangaId);

        Rate rateDocument = new Rate();
        rateDocument.setKey(rateKey);
        rateDocument.setRate(rate);

        kafkaTemplate.send(mangaRateTopic, mangaId, new RateEvent(rate, System.currentTimeMillis()));

        return rateRepository.save(rateDocument).then();
    }

    private void validateRate(Integer rate) {
        if (rate < 1 || rate > 5) {
            throw new IllegalArgumentException("Rate should be between 1 and 5");
        }
    }
}
