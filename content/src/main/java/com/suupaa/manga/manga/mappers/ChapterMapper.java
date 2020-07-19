package com.suupaa.manga.manga.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.suupaa.manga.manga.dto.ChapterTO;
import com.suupaa.manga.manga.entity.Chapter;

@Mapper(componentModel = "spring", uses = PageMapper.class)
public interface ChapterMapper {

    ChapterTO toChapterTO(Chapter manga);

    Chapter toChapterEntity(ChapterTO manga);

    List<ChapterTO> toChapterToList(List<Chapter> chapters);

}
