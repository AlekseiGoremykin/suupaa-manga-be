package com.suupaa.manga.manga.mappers;

import org.mapstruct.Mapper;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.entity.Chapter;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    ChapterTO toChapterTO(Chapter manga);

    Chapter toChapterEntity(ChapterTO manga);

}
