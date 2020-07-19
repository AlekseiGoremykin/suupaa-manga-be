package com.suupaa.manga.frontend.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("upload/{mangaId}")
public class FEUploadController {

    @Autowired
    private FEUploadService uploadService;

    @GetMapping
    public String uploadPage(@PathVariable("mangaId") Long mangaId, Model model) {
        model.addAttribute("mangaId", mangaId);
        return "upload/uploadForm";
    }

    @PostMapping
    public String handleUpload(@PathVariable("mangaId") Long mangaId,
                               @RequestParam("chapterName") String chapterName,
                               @RequestParam("file") MultipartFile file) {

        final Long chapterId = uploadService.uploadChapter(mangaId, chapterName, file.getResource());
        return String.format("redirect:/mangas/%s/chapters/%s", mangaId, chapterId);
    }
}
