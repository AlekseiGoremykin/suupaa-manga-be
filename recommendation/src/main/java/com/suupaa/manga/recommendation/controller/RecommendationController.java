package com.suupaa.manga.recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.recommendation.controller.dto.MangaRating;
import com.suupaa.manga.recommendation.service.RecommendationService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("recommendations")
@Slf4j
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("top-5-rated-all-time")
    public Flux<MangaRating> mostRatedAllTime() {
        return recommendationService.mostRatedAllTime();
    }

    @GetMapping("trending")
    public Flux<MangaRating> trending() {
        return recommendationService.trending();
    }

    @GetMapping("most-viewed-today")
    public Flux<String> mostViewedToday() {
        return Flux.empty();
    }
}
