package com.example.jpastudy.base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResultDto {
    private String code;
    private String msg;

    public ResultDto(String code) {
        this.code = code;
        if("00".equals(this.code)) this.msg="정상";
        if("11".equals(this.code)) this.msg="실패";
    }

}
