package com.suupaa.manga.manga.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.dto.MangaTO;
import com.suupaa.manga.manga.entity.Image;
import com.suupaa.manga.manga.entity.Manga;
import com.suupaa.manga.manga.mappers.MangaMapper;
import com.suupaa.manga.manga.repository.ImageRepository;
import com.suupaa.manga.manga.repository.MangaRepository;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class MangaService {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaMapper mangaMapper;

    @Autowired
    private ImageRepository imageRepository;

    public Flux<MangaTO> getMangaList() {
        return mangaRepository.findAll()
                .map(mangaMapper::toMangaTo);
    }

    public Mono<MangaTO> getMangaById(String id) {
        return mangaRepository.findById(id)
                .map(mangaMapper::toMangaTo);
    }

    @SneakyThrows
    public Mono<MangaTO> create(Mono<MangaTO> mangaTo, FilePart cover) {

        final Path tempFile = Files.createTempFile(null, null);

        final Mono<byte[]> coverBytes = Mono.create(monoSink -> {
            try {
                monoSink.success(Files.readAllBytes(tempFile));
            } catch (IOException e) {
                monoSink.error(e);
            }
        });

        final Mono<Image> savedImage = cover.transferTo(tempFile)
                .then(coverBytes)
                .flatMap(bytes -> imageRepository.save(new Image(bytes)));

        final Mono<Manga> savedManga = Mono
                .zip(mangaTo, savedImage)
                .flatMap(t -> {
                    final MangaTO mangaTO = t.getT1();
                    final Image image = t.getT2();

                    validateManga(mangaTO);
                    final Manga mangaEntity = mangaMapper.toMangaEntity(mangaTO);
                    mangaEntity.setCoverImageId(image.getId());

                    return mangaRepository.save(mangaEntity);
                });


        return savedManga.map(mangaMapper::toMangaTo);
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
