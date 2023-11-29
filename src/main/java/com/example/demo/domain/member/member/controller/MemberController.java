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

    @PostMapping("/member/join")
    String doWrite(@Valid JoinForm joinForm){

        Member member = memberService.join(joinForm.username, joinForm.password);

        return rq.redirect("/member/login","회원가입이 완료되었습니다.");
    }

    @GetMapping("/member/login")
    String login(){
        return "member/member/login";
    }

    @Data
    public static class LoginForm{
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/member/login")
    String doLogin(@Valid LoginForm loginForm, HttpServletRequest req, HttpServletResponse response){
        Member member = memberService.findByUsername(loginForm.username).get();
        
        if( !member.getPassword().equals(loginForm.password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        rq.setSessionAttr("loginedMemberId",member.getId()); // rq = 현재 접속한 브라우저
        rq.setSessionAttr("authorities",member.getAuthorities() );

        return rq.redirect("/article/list","로그인이 완료되었습니다.");
    }

    @GetMapping("/member/logout")
    String logout(){
        rq.removeSessionAttr("loginedMemberId");

        return rq.redirect("/article/list","로그아웃 되었습니다.");
    }
}

