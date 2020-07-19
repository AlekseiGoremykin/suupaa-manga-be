package com.suupaa.manga.frontend.manga.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MangaTO {
    private Long id;
    private String name;
    private String genre;

    private List<ChapterTO> chapters = new ArrayList<>();

}
