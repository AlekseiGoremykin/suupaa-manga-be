package com.suupaa.manga.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.identity.IdentityConstant;
import com.suupaa.manga.user.dto.UserProfileTO;
import com.suupaa.manga.user.entity.UserProfile;
import com.suupaa.manga.user.mapper.UserProfileMapper;
import com.suupaa.manga.user.repository.UserProfileRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileController {

    @Autowired
    private UserProfileRepository repository;

    @Autowired
    private UserProfileMapper mapper;

    @GetMapping
    public Flux<UserProfileTO> list() {
        return Flux.empty();
    }

    @GetMapping("current")
    public Mono<UserProfileTO> getCurrentUser(@RequestHeader(IdentityConstant.IDENTITY_HEADER) final String id) {
        //TODO make it reactive
        if (id == null) {
            return Mono.error(() -> new IllegalArgumentException("no user"));
        }
        return repository.findById(id)
                .switchIfEmpty(this.repository.save(createUserProfile(id)))
                .map(mapper::toUserProfileTO);
    }

    private UserProfile createUserProfile(String email) {
        final UserProfile userProfile = new UserProfile();
        userProfile.setEmail(email);
        return userProfile;
    }
}
