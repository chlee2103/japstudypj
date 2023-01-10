package com.example.jpastudy.base.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
public class ResultVO {
    private String code;
    private String msg;

    public ResultVO(String code) {
        this.code = code;
        if("00".equals(this.code)) this.msg="정상";
        if("11".equals(this.code)) this.msg="실패";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultVO resultVo = (ResultVO) o;
        return Objects.equals(code, resultVo.code) && Objects.equals(msg, resultVo.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg);
    }
}
