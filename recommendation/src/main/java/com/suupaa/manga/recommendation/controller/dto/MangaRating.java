package com.suupaa.manga.recommendation.controller.dto;

import com.suupaa.manga.recommendation.listener.model.MangaRate;

import lombok.Data;

@Data
public class MangaRating {

    private final String mangaId;
    private final String rate;

    public static MangaRating from(MangaRate mangaRate) {
        return new MangaRating(
                mangaRate.getMangaId(),
                String.format("%.2f", mangaRate.average())
        );
    }
}
