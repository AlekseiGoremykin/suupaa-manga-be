package com.suupaa.manga.manga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.manga.service.RateService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "mangas/{mangaId}/rates/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RateController {

    @Autowired
    private RateService rateService;

    @PutMapping(value = "{rate}")
    public Mono<Void> rate(@PathVariable("mangaId") String mangaId, @PathVariable("rate") Integer rate) {
        return rateService.rate(mangaId, rate);
    }

}
