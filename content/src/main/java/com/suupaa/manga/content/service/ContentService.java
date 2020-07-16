package com.suupaa.manga.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suupaa.manga.content.model.Content;
import com.suupaa.manga.content.repository.ContentRepository;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getContentList() {
        return contentRepository.findAll();
    }

    public Content getContentById(Long id) {
        //TODO
        return contentRepository.findById(id).get();
    }
}
