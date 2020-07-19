package com.suupaa.manga.frontend.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.suupaa.manga.frontend.manga.model.ChapterTO;

@Service
public class FEUploadService {

    @Autowired
    private RestTemplate restTemplate;

    public Long uploadChapter(Long mangaId, String chapterName, Resource data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", data);
        final ChapterTO chapter = new ChapterTO();
        chapter.setName(chapterName);
        body.add("chapter", chapter);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        final String url = "http://localhost:8081/mangas/{mangaId}/chapters";
        ResponseEntity<Long> response = restTemplate.postForEntity(url, requestEntity, Long.class, mangaId);

        return response.getBody();
    }
}
