package com.suupaa.manga.content.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.content.model.Content;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentController {

    @GetMapping
    public Flux<Content> list() {
        return Flux.empty();
    }

    @GetMapping("/{id}")
    public Mono<Content> getById(@PathVariable("id") final long id) {
        return Mono.just(new Content(-1L, "mob psycho 100"));
    }

}
