package com.suupaa.manga.manga.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.entity.Chapter;
import com.suupaa.manga.manga.entity.Image;
import com.suupaa.manga.manga.entity.Page;
import com.suupaa.manga.manga.mappers.ChapterMapper;
import com.suupaa.manga.manga.repository.ChapterRepository;
import com.suupaa.manga.manga.repository.ImageRepository;
import com.suupaa.manga.manga.repository.PageRepository;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
@Transactional
public class UploadService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ChapterMapper chapterMapper;

    @SneakyThrows
    public Mono<String> upload(String mangaId, Mono<ChapterTO> chapterTo, FilePart filePart) {
        final Mono<String> chapterId = saveChapter(mangaId, chapterTo);

        final Path tempFile = Files.createTempFile(null, null);
        final Mono<Void> zipFile = filePart.transferTo(tempFile);

        final Flux<Tuple2<Integer, byte[]>> names = readZip(tempFile, zipFile);
        final Flux<Tuple2<Integer, String>> images = saveImages(names);
        final Flux<Page> pages = savePages(chapterId, images);

        return pages
                .last()
                .map(Page::getChapterId);
    }

    private Flux<Page> savePages(Mono<String> chapterId, Flux<Tuple2<Integer, String>> images) {
        return Flux.combineLatest(chapterId, images, (chId, image) -> {
            Page page = new Page();
            page.setNumber(image.getT1());
            page.setImageId(image.getT2());
            page.setChapterId(chId);

            return pageRepository.save(page);
        }).flatMap(Function.identity());
    }

    private Flux<Tuple2<Integer, String>> saveImages(Flux<Tuple2<Integer, byte[]>> names) {
        return names.map(pageData -> {
            Image image = new Image(pageData.getT2());
            return imageRepository.save(image).map(im -> Tuples.of(pageData.getT1(), im.getId()));
        }).flatMap(Function.identity());
    }

    private Flux<Tuple2<Integer, byte[]>> readZip(Path tempFile, Mono<Void> zipFile) {
        return zipFile.thenMany(Flux.create((sink) -> {
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(tempFile))) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        continue;
                    }

                    sink.next(Tuples.of(toNumber(entry.getName()), IOUtils.toByteArray(zipInputStream)));
                }
            } catch (IOException e) {
                sink.error(e);
            }
            sink.complete();
        }));
    }

    private Mono<String> saveChapter(String mangaId, Mono<ChapterTO> chapterTo) {
        return chapterTo
                .map(manga -> {
                    final Chapter chapter = chapterMapper.toChapterEntity(manga);
                    chapter.setMangaId(mangaId);
                    return chapter;
                })
                .flatMap(chapterRepository::save)
                .map(Chapter::getId);
    }

    private Integer toNumber(String number) {
        final String onlyDigits = number.replaceAll("[^\\d]", "");
        return Integer.parseInt(onlyDigits, 10);
    }
}
