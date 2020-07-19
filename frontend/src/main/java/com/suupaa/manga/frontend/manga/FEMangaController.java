package com.suupaa.manga.frontend.manga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suupaa.manga.frontend.manga.model.MangaTO;

@Controller
@RequestMapping("mangas")
public class FEMangaController {

    @Autowired
    private FEMangaService contentService;

    @GetMapping
    public String mangas(Model model) {
        model.addAttribute("mangas", contentService.getMangaList());
        return "mangas/list";
    }

    @GetMapping("{id}")
    public String chapters(@PathVariable("id") Long id, Model model) {
        final MangaTO manga = contentService.getManga(id);
        model.addAttribute("chapters", manga.getChapters());
        model.addAttribute("mangaId", manga.getId());
        return "mangas/view";
    }

    @GetMapping("{mangaId}/chapters/{id}")
    public String pages(@PathVariable("mangaId") Long mangaId, @PathVariable("id") Long chapterId, Model model) {
        model.addAttribute("pages", contentService.getChapter(mangaId, chapterId).getPages());
        return "mangas/pages";
    }
}
