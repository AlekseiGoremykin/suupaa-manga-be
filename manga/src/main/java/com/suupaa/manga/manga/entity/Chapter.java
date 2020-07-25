package com.suupaa.manga.manga.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Chapter {

    @Id
    private String id;
    private String teamId;
    private String name;
    private String mangaId;

}
