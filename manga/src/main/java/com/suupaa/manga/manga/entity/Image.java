package com.suupaa.manga.manga.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Image {

    @Id
    private String id;
    private byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }
}
