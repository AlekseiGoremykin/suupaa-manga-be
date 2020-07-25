package com.suupaa.manga.manga.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Page {

    @Id
    private String id;
    private Integer number;
    private String imageId;
    private String chapterId;
}
