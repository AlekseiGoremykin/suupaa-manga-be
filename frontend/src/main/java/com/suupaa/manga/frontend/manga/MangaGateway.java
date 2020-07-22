package com.suupaa.manga.frontend.manga;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import com.suupaa.manga.frontend.manga.model.ChapterTO;
import com.suupaa.manga.frontend.manga.model.MangaTO;

@Service
public class MangaGateway {

    @Value("${services.manga-api}")
    private String mangaService;

    @Autowired
    private RestTemplate restTemplate;

    private UriBuilderFactory uriBuilderFactory;

    @PostConstruct
    public void init() {
        uriBuilderFactory = new DefaultUriBuilderFactory(mangaService);
    }

    public List<MangaTO> getMangaList() {
        final URI uri = uriBuilderFactory.builder()
                .pathSegment("mangas")
                .build();
        MangaTO[] mangas = restTemplate.getForObject(uri, MangaTO[].class);
        return mangas == null ? Collections.emptyList() : Arrays.asList(mangas);
    }

    public MangaTO getManga(Long id) {
        final URI uri = uriBuilderFactory.builder()
                .pathSegment("mangas")
                .pathSegment("{id}")
                .build(id);
        return restTemplate.getForObject(uri, MangaTO.class);
    }

    public ChapterTO getChapter(Long id, Long chapterId) {
        return getManga(id).getChapters().stream()
                .filter(chapterTO -> chapterTO.getId().equals(chapterId))
                .findFirst()
                .orElse(null);
    }
}
