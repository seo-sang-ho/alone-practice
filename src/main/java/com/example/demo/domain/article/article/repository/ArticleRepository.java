package com.example.demo.domain.article.article.repository;

import com.example.demo.domain.article.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>(){{
        add(new Article(1L,"제목1","내용1"));
        add(new Article(2L,"제목2","내용2"));
        add(new Article(3L,"제목3","내용3"));
    }};

    public void save(Article article) {
        if(article.getId() == null){
            article.setId(articles.size() + 1L);
        }
        articles.add(article);
    }

    public Article findLastArticle() {
        return articles.getLast();
    }

    public List<Article> findAll() {
        return articles;
    }

    public Optional<Article> findById(long id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst();
    }

    public void delete(long id) {
        articles.removeIf(article -> article.getId() == id);
    }
}
