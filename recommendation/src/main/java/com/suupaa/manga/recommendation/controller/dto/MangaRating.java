package com.suupaa.manga.recommendation.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suupaa.manga.recommendation.listener.model.MangaRate;

import lombok.Data;

@Data
public class MangaRating {

    private final String mangaId;
    private final String rate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public MangaRating(@JsonProperty("mangaId") String mangaId, @JsonProperty("rate") String rate) {
        this.mangaId = mangaId;
        this.rate = rate;
    }

    public static MangaRating from(MangaRate mangaRate) {
        return new MangaRating(
                mangaRate.getMangaId(),
                String.format("%.2f", mangaRate.average())
        );
    }
}
