package com.suupaa.manga.user.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.user.model.UserProfile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/user-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileController {

    @GetMapping
    public Flux<UserProfile> list() {
        return Flux.empty();
    }

    @GetMapping("/{id}")
    public Mono<UserProfile> getById(@PathVariable("id") final long id) {
        return Mono.just(new UserProfile(-1L, "reader"));
    }

}
