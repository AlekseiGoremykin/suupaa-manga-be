package com.suupaa.manga.manga.mappers;

import org.mapstruct.Mapper;

import com.suupaa.manga.manga.dto.MangaTO;
import com.suupaa.manga.manga.entity.Manga;

@Mapper(componentModel = "spring")
public interface MangaMapper {

    MangaTO toMangaTo(Manga manga);

    Manga toMangaEntity(MangaTO manga);

}
