package com.example.demo.domain.article.article.controller;

import com.example.demo.domain.article.article.entity.Article;
import com.example.demo.domain.article.article.service.ArticleService;
import com.example.demo.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/article/write")
    String showWrite(){
        return "article/write";
    }

    @PostMapping("/article/write")
    @ResponseBody
    RsData<Article> doWrite(String title, String body){

        Article article = articleService.write(title, body);

        RsData<Article> rs = new RsData<>("S-1","%d번 게시물이 추가되었습니다".formatted(article.getId()),article);

        return rs;
    }

    @GetMapping("/article/getLastArticle")
    @ResponseBody
    Article getLastArticle(){
        return articleService.findLastArticle();
    }

    @GetMapping("/article/getArticles")
    @ResponseBody
    List<Article> getArticles(){
        return articleService.findAll();
    }
}

