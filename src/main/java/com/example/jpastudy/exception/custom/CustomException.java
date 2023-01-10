package com.example.jpastudy.exception.custom;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private CustomErrorCode errorCode;

    public CustomException(String message, CustomErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
