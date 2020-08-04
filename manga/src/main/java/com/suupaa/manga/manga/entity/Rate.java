package com.suupaa.manga.manga.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Setter
@Getter
public class Rate {

    @Id
    private RateKey key;
    private Integer rate;

    @Getter
    @Setter
    public static class RateKey {
        private String userId;
        private String mangaId;
    }
}
