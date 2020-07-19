package com.suupaa.manga.manga.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.service.UploadService;

@RestController
@RequestMapping(value = "/mangas/{mangaId}/chapters", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping
    public Long upload(@PathVariable("mangaId") Long mangaId,
                       @RequestPart("chapter") ChapterTO chapter,
                       @RequestPart("file") MultipartFile file) throws IOException {
        return uploadService.upload(mangaId, chapter, file.getInputStream());
    }

}
