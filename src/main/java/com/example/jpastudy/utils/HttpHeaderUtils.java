package com.example.jpastudy.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class HttpHeaderUtils {

    public static HttpHeaders headerToken(String token){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        header.set("X-AUTH-TOKEN", token);
        return header;
    }

}
