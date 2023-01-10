package com.example.jpastudy.jwttoken.service;

import com.example.jpastudy.base.dto.ResultDto;
import com.example.jpastudy.exception.custom.CustomErrorCode;
import com.example.jpastudy.exception.custom.CustomException;
import com.example.jpastudy.jwttoken.controller.dto.MemberDto;
import com.example.jpastudy.jwttoken.domain.Member;
import com.example.jpastudy.jwttoken.domain.MemberRepository;
import com.example.jpastudy.jwttoken.token.JwtTokenProvider;
import com.example.jpastudy.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public ResultDto join(MemberDto memberDto) {
        if(this.getMemberByEmail(memberDto.getEmail()).isPresent()) {
            throw new CustomException("Email duplication ", CustomErrorCode.MEMBER_EMAIL_DUPLICATION);
        }

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .password(passwordEncoder.encode(memberDto.getPassword()))  // pw 인코딩
                .roles(Collections.singletonList("ROLE_USER"))              // roles는 최초 user로 설정
                .build();

        long id = memberRepository.save(member).getId();
        String code = id>0?"00":"11";
        ResultDto result = new ResultDto(code);

        return result;
    }

    @Transactional
    public ResponseEntity<?> login(MemberDto memberDto) {
        Member member = this.getMemberByEmail(memberDto.getEmail())
                .orElseThrow(() -> new CustomException("Not email ", CustomErrorCode.MEMBER_NOT_EMAIL)); // orElseThrow : 없을경우 예외처리

        if(!passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
            throw new CustomException("Wrong password ", CustomErrorCode.MEMBER_WRONG_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
        String code = token != null && !"".equals(token)?"00":"11";

        HttpHeaders header = HttpHeaderUtils.headerToken(token);
        ResultDto result = new ResultDto(code);
        // 로그인에 성공하면 email, roles 로 토큰 생성 후 반환

        return ResponseEntity.ok().headers(header).body(result);
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public boolean joinValidationCheck(MemberDto memberDto) {
        if(StringUtils.isEmpty(memberDto.getEmail())){
            throw new CustomException("Not info email", CustomErrorCode.MEMBER_NOT_EMAIL);
        }
        if(StringUtils.isEmpty(memberDto.getPassword())) {
            throw new CustomException("Not info password", CustomErrorCode.MEMBER_NOT_INFO_PASSWORD);
        }
        if(StringUtils.isEmpty(memberDto.getNickname())) {
            throw new CustomException("Not info nickname", CustomErrorCode.MEMBER_NOT_INFO_NICKNAME);
        }

        return true;
    }
}
