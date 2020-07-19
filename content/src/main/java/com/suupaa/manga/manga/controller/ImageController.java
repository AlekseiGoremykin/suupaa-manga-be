package com.suupaa.manga.manga.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.manga.service.ImageService;

@RestController
@RequestMapping(value = "images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("{id}")
    public void getImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(new ByteArrayInputStream(imageService.getImage(id)), response.getOutputStream());
    }

}
