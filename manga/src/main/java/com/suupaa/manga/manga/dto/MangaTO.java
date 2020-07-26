package com.suupaa.manga.manga.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MangaTO {

    private String id;
    private String name;
    private String genre;
    private String coverImageId;

    private List<ChapterTO> chapters = new ArrayList<>();

}
