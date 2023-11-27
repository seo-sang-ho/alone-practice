package com.example.demo.domain.member.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Member {
    private Long id;
    private String username;
    private String password;

    public Member(String username, String password){
        this.username = username;
        this.password = password;
    }
}
