package com.suupaa.manga.frontend.manga.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ChapterTO {
    private Long id;
    private String name;
    private String teamId;

    private List<PageTO> pages = new ArrayList<>();

}
