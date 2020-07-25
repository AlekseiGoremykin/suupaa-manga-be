package com.suupaa.manga.manga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.service.ChapterService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/mangas/{mangaId}/chapters", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping
    public Flux<ChapterTO> list(@PathVariable("mangaId") String mangaId) {
        return chapterService.getChaptersByMangaId(mangaId);
    }
}
