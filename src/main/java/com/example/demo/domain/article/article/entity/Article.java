package com.example.demo.domain.article.article.entity;


import com.example.demo.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Article {
    private Long id;
    private Member author;
    private String title;
    private String body;

    public Article(Member author, String title, String body){
        this.author = author;
        this.title = title;
        this.body = body;
    }
}
