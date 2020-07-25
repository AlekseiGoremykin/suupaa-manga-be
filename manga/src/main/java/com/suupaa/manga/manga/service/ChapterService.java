package com.suupaa.manga.manga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.mappers.ChapterMapper;
import com.suupaa.manga.manga.repository.ChapterRepository;

import reactor.core.publisher.Flux;

@Service
@Transactional
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ChapterMapper chapterMapper;

    public Flux<ChapterTO> getChaptersByMangaId(String mangaId) {
        return chapterRepository.findByMangaIdOrderByName(mangaId)
                .map(chapterMapper::toChapterTO);
    }
}
