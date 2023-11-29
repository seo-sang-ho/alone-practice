package com.example.demo.global.initData;

import com.example.demo.domain.article.article.service.ArticleService;
import com.example.demo.domain.member.member.entity.Member;
import com.example.demo.domain.member.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!Prod")
@Configuration
public class NotProd {

    @Bean
    public ApplicationRunner initNotProd(MemberService memberService, ArticleService articleService){
        return args -> {
            Member admin = memberService.join("admin", "1234");
            Member user1 = memberService.join("user1", "1234");
            Member user2 = memberService.join("user2", "1234");

            articleService.write(admin, "제목1", "내용1");
            articleService.write(user1, "제목2", "내용2");
            articleService.write(user1, "제목3", "내용3");
        };
    }
}