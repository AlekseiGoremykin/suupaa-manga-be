package com.suupaa.manga.manga.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Manga {

    @Id
    private String id;
    private String name;
    private String genre;
}
