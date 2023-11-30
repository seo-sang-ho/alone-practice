package com.example.demo.domain.article.article.controller;

import com.example.demo.domain.article.article.entity.Article;
import com.example.demo.domain.article.article.service.ArticleService;
import com.example.demo.domain.member.member.service.MemberService;
import com.example.demo.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final Rq rq;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    String modify(Model model,@PathVariable long id){
        Article article = articleService.findById(id).get();

        if(!articleService.canModify(rq.getMember(),article)){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        model.addAttribute("articleService",this);
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

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{id}")
    String modify(@Valid ModifyForm modifyForm, @PathVariable("id") long id){
        Article article = articleService.findById(id).get();

        if(!articleService.canModify(rq.getMember(),article)){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        articleService.modify(article,modifyForm.title, modifyForm.body);

        return rq.redirect("/article/list", "%d번 게시물 수정되었습니다.".formatted(id));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    String delete(@PathVariable long id){
        Article article = articleService.findById(id).get();

        if(!articleService.canDelete(rq.getMember(),article)){
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        articleService.delete(article);

        return rq.redirect("/article/list","%d번 게시물 삭제되었습니다.".formatted(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    String doWrite(@Valid WriteForm writeForm){

        Article article = articleService.write(rq.getMember(),writeForm.title, writeForm.body);

        return rq.redirect("/article/list","%d번 게시물이 생성되었습니다".formatted(article.getId()));
    }

    @GetMapping("/detail/{id}")
    String showDetail(Model model,@PathVariable long id){
        Article article = articleService.findById(id).get();

        model.addAttribute("article",article);

        return "article/article/detail";
    }

    @GetMapping("/getLastArticle")
    @ResponseBody
    Article getLastArticle(){
        return articleService.findLastArticle();
    }

    @GetMapping("/getArticles")
    @ResponseBody
    List<Article> getArticles(){
        return articleService.findAll();
    }

    @GetMapping("/list")
    String showList(Model model,HttpServletRequest req){
        List<Article> articles = articleService.findAll();

        model.addAttribute("articles",articles);

        return "article/article/list";
    }

}

