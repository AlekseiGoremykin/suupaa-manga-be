package com.suupaa.manga.manga.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class MangaTO {

    private Long id;
    private String name;
    private String genre;
    private Set<ChapterTO> chapters = new HashSet<>();

}
