package com.suupaa.manga.manga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.dto.MangaTO;
import com.suupaa.manga.manga.entity.Manga;
import com.suupaa.manga.manga.mappers.MangaMapper;
import com.suupaa.manga.manga.repository.MangaRepository;

@Service
@Transactional
public class MangaService {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaMapper mangaMapper;

    public List<MangaTO> getMangaList() {
        return mangaMapper.toMangaToList(mangaRepository.findAll());
    }

    public MangaTO getMangaById(Long id) {
        return mangaMapper.toMangaTo(mangaRepository.getOne(id));
    }

    public MangaTO create(MangaTO mangaTo) {
        validateManga(mangaTo);
        Manga manga = mangaMapper.toMangaEntity(mangaTo);
        return mangaMapper.toMangaTo(mangaRepository.save(manga));
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
