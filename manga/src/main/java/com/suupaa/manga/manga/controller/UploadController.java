package com.suupaa.manga.manga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.service.UploadService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/mangas/{mangaId}/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> upload(@PathVariable("mangaId") String mangaId,
                               @RequestPart("chapter") Mono<ChapterTO> chapter,
                               @RequestPart("file") FilePart file) {
        return uploadService.upload(mangaId, chapter, file);
    }

}
