package com.suupaa.manga.manga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.entity.Image;
import com.suupaa.manga.manga.repository.ImageRepository;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public byte[] getImage(Long id) {
        final Image image = imageRepository.getOne(id);
        return image.getImage();
    }
}
