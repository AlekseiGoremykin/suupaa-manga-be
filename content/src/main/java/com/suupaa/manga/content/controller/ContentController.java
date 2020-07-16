package com.suupaa.manga.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.content.model.Content;
import com.suupaa.manga.content.service.ContentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public Flux<Content> list() {
        return Flux.fromIterable(contentService.getContentList());
    }

    @GetMapping("/{id}")
    public Mono<Content> getById(@PathVariable("id") final long id) {
        return Mono.just(contentService.getContentById(id));
    }

}
