package com.example.jpastudy.erroer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400,"COMMON-ERR-400","BAD REQUEST"),
    UNAUTHORIZED(401,"COMMON-ERR-401","UNAUTHORIZED"),
    PAYMENT_REQUIRED(402,"COMMON-ERR-402","PAYMENT REQUIRED"),
    FORBIDDEN(403,"COMMON-ERR-403","FORBIDDEN"),
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    METHOD_NOT_ALLOWED(405,"COMMON-ERR-405","METHOD NOT ALLOWED"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    ;

    private int status;
    private String errorCode;
    private String message;
}
