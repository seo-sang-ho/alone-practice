package com.example.demo.domain.article.article.repository;

import com.example.demo.domain.article.article.entity.Article;
import com.example.demo.domain.member.member.entity.Member;
import com.example.demo.domain.member.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

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
