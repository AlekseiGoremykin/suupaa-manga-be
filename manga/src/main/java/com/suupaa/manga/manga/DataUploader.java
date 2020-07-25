package com.suupaa.manga.manga;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.stereotype.Component;

import com.suupaa.manga.manga.entity.Chapter;
import com.suupaa.manga.manga.entity.Manga;
import com.suupaa.manga.manga.repository.ChapterRepository;
import com.suupaa.manga.manga.repository.MangaRepository;

import lombok.SneakyThrows;

@SuppressWarnings("unchecked")
@Component
@Profile("dev")
public class DataUploader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        final Jackson2ResourceReader resourceReader = new Jackson2ResourceReader();
        Collection<Manga> mangas =
                (Collection<Manga>) resourceReader.readFrom(new ClassPathResource("/db/manga.json"),
                        this.getClass().getClassLoader());

        mangaRepository.deleteAll().block();
        mangaRepository.saveAll(mangas).collectList().block();

        Collection<Chapter> chapters =
                (Collection<Chapter>) resourceReader.readFrom(new ClassPathResource("/db/chapters.json"),
                        this.getClass().getClassLoader());

        chapterRepository.deleteAll().block();
        chapterRepository.saveAll(chapters).collectList().block();
    }

}
