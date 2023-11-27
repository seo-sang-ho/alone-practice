package com.example.demo.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class RsData<T> {
    private final String resultCode;
    private final String msg;
    private T data;
}

