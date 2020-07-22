package com.suupaa.manga.frontend.upload;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import com.suupaa.manga.frontend.manga.model.ChapterTO;

@Service
public class UploadGateway {

    @Value("${services.manga-api}")
    private String mangaService;

    @Autowired
    private RestTemplate restTemplate;

    private UriBuilderFactory uriBuilderFactory;

    @PostConstruct
    public void init() {
        uriBuilderFactory = new DefaultUriBuilderFactory(mangaService);
    }

    public Long uploadChapter(Long mangaId, String chapterName, Resource data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", data);
        final ChapterTO chapter = new ChapterTO();
        chapter.setName(chapterName);
        body.add("chapter", chapter);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        final URI uri = uriBuilderFactory.builder()
                .pathSegment("mangas")
                .pathSegment("{mangaId}")
                .pathSegment("chapters")
                .build(mangaId);
        ResponseEntity<Long> response = restTemplate.postForEntity(uri, requestEntity, Long.class);

        return response.getBody();
    }
}
