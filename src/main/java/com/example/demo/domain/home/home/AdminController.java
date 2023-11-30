package com.example.demo.domain.home.home;

import com.example.demo.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdminController {

    private final Rq rq;

    @GetMapping("")
    public String showMain(){
        return "home/home/adm/main";
    }

    @GetMapping("/home/about")
    public String showAbout (){
        return "home/home/adm/about";
    }
}
