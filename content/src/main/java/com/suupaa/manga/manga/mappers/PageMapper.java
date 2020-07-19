package com.suupaa.manga.manga.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.suupaa.manga.manga.dto.PageTO;
import com.suupaa.manga.manga.entity.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    PageTO toPageTO(Page page);

    List<PageTO> toPageToList(List<Page> pages);

}
