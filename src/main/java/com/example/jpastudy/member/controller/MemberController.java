package com.example.jpastudy.member.controller;

import com.example.jpastudy.base.vo.ResultVO;
import com.example.jpastudy.member.dto.MemberDto;
import com.example.jpastudy.member.service.MemberService;
import com.example.jpastudy.jwttoken.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public ResponseEntity<ResultVO> join(@Valid @RequestBody MemberDto memberDto) throws Exception {
        ResultVO result = null;
        if(memberService.joinValidationCheck(memberDto)){
            result = memberService.join(memberDto);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto, HttpServletRequest request) {
        /*String token = jwtTokenProvider.resoleveToken(request);
        log.info("token {}", token);*/
        return memberService.login(memberDto);
    }

    @PutMapping("/update")
    public String update(@RequestBody MemberDto memberDto) {

        return null;
    }
}
