package com.suupaa.manga.manga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.manga.service.ImageService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<byte[]> getImage(@PathVariable("id") String id) {
        return imageService.getImage(id);
    }

}
