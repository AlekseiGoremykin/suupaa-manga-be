package com.suupaa.manga.manga.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.suupaa.manga.manga.dto.MangaTO;
import com.suupaa.manga.manga.entity.Manga;

@Mapper(componentModel = "spring", uses = ChapterMapper.class)
public interface MangaMapper {

    MangaTO toMangaTo(Manga manga);

    Manga toMangaEntity(MangaTO manga);

    List<MangaTO> toMangaToList(List<Manga> manga);

}
