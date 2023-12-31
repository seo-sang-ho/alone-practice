package com.example.demo.domain.article.article.service;

import com.example.demo.domain.article.article.entity.Article;
import com.example.demo.domain.article.article.repository.ArticleRepository;
import com.example.demo.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article write(Member author, String title, String body) {
        Article article = new Article(author,title, body);

        articleRepository.save(article);

        return article;
    }

    public Article findLastArticle() {
        return articleRepository.findLastArticle();
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    public void modify(Article article, String title, String body) {
        article.setTitle(title);
        article.setBody(body);
    }

    public boolean canModify(Member actor, Article article) {
        if(actor == null ) return false;

        return article.getAuthor().equals(actor);
    }

    public boolean canDelete(Member actor, Article article) {
        if(actor == null ) return false;

        if(actor.isAdmin()) return true;  // 관리자는 삭제를 할 수 있어야함

        return article.getAuthor().equals(actor);
    }
}
