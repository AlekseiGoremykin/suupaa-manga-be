package com.suupaa.manga.manga.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class ChapterTO {

    private Long id;
    private Long teamId;
    private String name;
    private Long mangaId;

    private Set<PageTO> pages = new HashSet<>();

}
