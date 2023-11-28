package com.example.demo.domain.article.article.controller;

import com.example.demo.domain.article.article.entity.Article;
import com.example.demo.domain.article.article.service.ArticleService;
import com.example.demo.domain.member.member.entity.Member;
import com.example.demo.domain.member.member.service.MemberService;
import com.example.demo.global.rq.Rq;
import com.example.demo.global.rsData.RsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Validated
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/article/modify/{id}")
    String modify(Model model,@PathVariable long id){
        Article article = articleService.findById(id).get();

        model.addAttribute("article",article);

        String msg = "id %d, article deleted".formatted(id);

        return "article/article/modify";
    }

    @Data
    public static class ModifyForm{
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @PostMapping("/article/modify/{id}")
    String modify(@Valid ModifyForm modifyForm, @PathVariable("id") long id){

        articleService.modify(id,modifyForm.title, modifyForm.body);

        return rq.redirect("/article/list", "%d번 게시물 수정되었습니다.".formatted(id));
    }

    @GetMapping("/article/delete/{id}")
    String delete(@PathVariable long id){
        articleService.delete(id);

        return rq.redirect("/article/list","%d번 게시물 삭제되었습니다.".formatted(id));
    }

    @GetMapping("/article/write")
    String showWrite(){
        return "article/article/write";
    }

    @Data
    public static class WriteForm{
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @PostMapping("/article/write")
    String doWrite(@Valid WriteForm writeForm, HttpServletRequest req, HttpServletResponse resp){

        Article article = articleService.write(writeForm.title, writeForm.body);

        return rq.redirect("/article/list","%d번 게시물이 생성되었습니다".formatted(article.getId()));
    }

    @GetMapping("/article/detail/{id}")
    String showDetail(Model model,@PathVariable long id){
        Article article = articleService.findById(id).get();

        model.addAttribute("article",article);

        return "article/article/detail";
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

    @GetMapping("/article/list")
    String showList(Model model,HttpServletRequest req){
        long opLoginedMemberId = Optional.ofNullable(req.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                .map(Cookie::getValue)
                .mapToLong(Long::parseLong)
                .findFirst()
                .orElse(0);


        if( opLoginedMemberId > 0 ){
            Member loginedMember = memberService.findById(opLoginedMemberId).get();
            model.addAttribute("loginedMember",loginedMember);
        }

        long fromSessionLoginMemberId = 0;

        if(req.getSession().getAttribute("loginedMemberId") != null){
            fromSessionLoginMemberId = (long) req.getSession().getAttribute("loginedMemberId");
        }

        if(fromSessionLoginMemberId > 0 ){
            Member fromSessionLoginedMember = memberService.findById(fromSessionLoginMemberId).get();
            model.addAttribute("fromSessionLoginedMember",fromSessionLoginedMember);
        }

        List<Article> articles = articleService.findAll();

        model.addAttribute("articles",articles);

        return "article/article/list";
    }

}

