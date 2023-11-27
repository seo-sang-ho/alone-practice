package com.example.demo.domain.article.home.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goToArticleList(){
        return "redirect:/article/list";
    }
}
