package com.example.demo.domain.member.member.controller;

import com.example.demo.domain.member.member.entity.Member;
import com.example.demo.domain.member.member.service.MemberService;
import com.example.demo.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/join")
    String showWrite(){
        return "member/member/join";
    }

    @Data
    public static class JoinForm{
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/join")
    String doWrite(@Valid JoinForm joinForm){

        Member member = memberService.join(joinForm.username, joinForm.password);

        return rq.redirect("/member/login","회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/login")
    String login(){
        return "member/member/login";
    }
}

