package com.suupaa.manga.manga.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Manga {

    @Id
    private String id;
    private String name;
    private String description;
    private List<String> alternativeNames;
    private List<String> genres;
    private int releaseYear;
    private String state;
    private MangaRate mangaRate;
    private String coverImageId;

    @Data
    public static class MangaRate {
        private Double rate;
        private Integer count;
    }
}
