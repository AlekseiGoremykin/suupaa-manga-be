package com.suupaa.manga.manga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suupaa.manga.manga.dto.MangaTO;
import com.suupaa.manga.manga.service.MangaService;

@RestController
@RequestMapping(value = "/mangas", produces = MediaType.APPLICATION_JSON_VALUE)
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @GetMapping
    public List<MangaTO> list() {
        return mangaService.getMangaList();
    }

    @GetMapping("/{id}")
    public MangaTO getById(@PathVariable("id") final long id) {
        return mangaService.getMangaById(id);
    }

    @PostMapping
    public MangaTO create(@RequestBody MangaTO manga) {
        return mangaService.create(manga);
    }

}
