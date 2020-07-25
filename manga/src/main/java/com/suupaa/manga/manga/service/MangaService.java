package com.suupaa.manga.manga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.dto.MangaTO;
import com.suupaa.manga.manga.entity.Manga;
import com.suupaa.manga.manga.mappers.MangaMapper;
import com.suupaa.manga.manga.repository.MangaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class MangaService {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaMapper mangaMapper;

    public Flux<MangaTO> getMangaList() {
        return mangaRepository.findAll()
                .map(mangaMapper::toMangaTo);
    }

    public Mono<MangaTO> getMangaById(String id) {
        return mangaRepository.findById(id)
                .map(mangaMapper::toMangaTo);
    }

    public Mono<MangaTO> create(MangaTO mangaTo) {
        validateManga(mangaTo);
        Manga manga = mangaMapper.toMangaEntity(mangaTo);
        return mangaRepository.save(manga).map(mangaMapper::toMangaTo);
    }

    public void validateManga(MangaTO manga) {
        if (manga.getName() == null) {
            throw new IllegalArgumentException("Name can not be empty");
        }

        if (manga.getId() != null) {
            throw new IllegalArgumentException("Id must be empty");
        }
    }
}
