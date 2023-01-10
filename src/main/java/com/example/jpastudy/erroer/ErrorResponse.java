package com.example.jpastudy.erroer;

import com.example.jpastudy.erroer.custom.CustomErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private String code;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }

    public ErrorResponse(CustomErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }

}
