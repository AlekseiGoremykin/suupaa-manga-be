package com.suupaa.manga.manga.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.entity.Chapter;
import com.suupaa.manga.manga.entity.Image;
import com.suupaa.manga.manga.entity.Manga;
import com.suupaa.manga.manga.entity.Page;
import com.suupaa.manga.manga.mappers.ChapterMapper;
import com.suupaa.manga.manga.repository.ChapterRepository;
import com.suupaa.manga.manga.repository.ImageRepository;
import com.suupaa.manga.manga.repository.MangaRepository;

@Service
@Transactional
public class UploadService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ChapterMapper chapterMapper;

    public Long upload(Long mangaId, ChapterTO chapterTo, InputStream zip) {
        Chapter chapter = chapterMapper.toChapterEntity(chapterTo);

        Set<Page> pages = new HashSet<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(zip)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                Image image = new Image();
                image.setImage(IOUtils.toByteArray(zipInputStream));
                final Image savedImage = imageRepository.save(image);

                Page page = new Page();
                page.setNumber(toNumber(entry.getName()));
                page.setImageId(savedImage.getId());
                pages.add(page);
            }
        } catch (IOException e) {
            LOG.error("Error occurred while traversing input archive", e);
        }

        chapter.setPages(pages);
        final Chapter savedChapter = chapterRepository.save(chapter);

        Manga manga = mangaRepository.getOne(mangaId);
        manga.getChapters().add(savedChapter);
        mangaRepository.save(manga);

        return savedChapter.getId();
    }

    private Integer toNumber(String number) {
        final String onlyDigits = number.replaceAll("[^\\d]", "");
        return Integer.parseInt(onlyDigits, 10);
    }
}
