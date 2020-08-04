package com.suupaa.manga.manga.dto;

import java.util.List;

import com.suupaa.manga.manga.entity.Manga;

import lombok.Data;

@Data
public class MangaTO {

    private String id;
    private String name;
    private String description;
    private List<String> alternativeNames;
    private List<String> genres;
    private int releaseYear;
    private Manga.MangaRate mangaRate;
    private String state;
    private String coverImageId;

}
