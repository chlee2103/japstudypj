package com.example.jpastudy.erroer.custom;

import lombok.Getter;


@Getter
public enum CustomErrorCode {

    MEMBER_EMAIL_DUPLICATION(400,"MEMBER-ERR-01","이미 가입된 이메일입니다."),
    MEMBER_NOT_INFO(400,"MEMBER-ERR-02","필수 파라미터 누락."),
    MEMBER_NOT_INFO_EMAIL(400,"MEMBER-ERR-02-1","필수 파라미터 누락 [email]"),
    MEMBER_NOT_INFO_PASSWORD(400,"MEMBER-ERR-02-2","필수 파라미터 누락 [password]"),
    MEMBER_NOT_INFO_NICKNAME(400,"MEMBER-ERR-02-3","필수 파라미터 누락 [nickname]"),
    MEMBER_NOT_EMAIL(400,"MEMBER-ERR-03","가입되지 않은 이메일입니다."),
    MEMBER_WRONG_PASSWORD(400, "MEMBER-ERR-04", "잘못된 비밀번호입니다."),
    ;

    private int status;
    private String errorCode;
    private String message;

    CustomErrorCode(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
