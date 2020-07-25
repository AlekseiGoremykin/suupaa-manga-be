package com.suupaa.manga.manga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.entity.Image;
import com.suupaa.manga.manga.repository.ImageRepository;

import reactor.core.publisher.Mono;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Mono<byte[]> getImage(String id) {
        return imageRepository.findById(id)
                .map(Image::getImage);
    }
}
