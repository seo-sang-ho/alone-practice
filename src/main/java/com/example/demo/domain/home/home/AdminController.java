package com.example.demo.domain.home.home;

import com.example.demo.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final Rq rq;

    @GetMapping("/adm")
    public String showMain(){
        return "home/home/adm/main";
    }

    @GetMapping("/adm/home/about")
    public String showAbout (){
        return "home/home/adm/about";
    }
}
