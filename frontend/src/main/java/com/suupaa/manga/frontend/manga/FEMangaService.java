package com.suupaa.manga.frontend.manga;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.suupaa.manga.frontend.manga.model.ChapterTO;
import com.suupaa.manga.frontend.manga.model.MangaTO;

@Service
public class FEMangaService {

    @Autowired
    private RestTemplate restTemplate;

    public List<MangaTO> getMangaList() {
        return Arrays.asList(restTemplate.getForObject("http://localhost:8081/mangas", MangaTO[].class));
    }

    public MangaTO getManga(Long id) {
        return restTemplate.getForObject("http://localhost:8081/mangas/{id}", MangaTO.class, id);
    }

    public ChapterTO getChapter(Long id, Long chapterId) {
        return getManga(id).getChapters().stream()
                .filter(chapterTO -> chapterTO.getId().equals(chapterId))
                .findFirst()
                .orElse(null);
    }
}
