package com.example.jpastudy.jwttoken.token;

import com.example.jpastudy.jwttoken.domain.MemberRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT에서 가장 핵심이 되는 토큰생성, 검증 등의 함수들을 구현해놓은 클래스

    private String secretKey = "1q222wer!@#$%^";

    private long tokenValidTime = 30 * 60 * 1000L;  // 토큰 유효시간 30분

    private final UserDetailsService userDetailsService;
    private final MemberRepository memberRepository;


    // 객체 초기화, secretKey -> Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 토큰생성
    public String createToken(String userPk, List<String> roles) {          // userPK = email
        Claims claims = Jwts.claims().setSubject(userPk);                   // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)                                          // 정보저장
                .setIssuedAt(now)                                           // 토큰 발생 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime))    // 토큰 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)              // 암호화 알고리즘, secret 값
                .compact();
    }

    // 인증정보조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 유효성, 만료일자 확인
    /*public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }*/

    // 토큰 유효성, 만료일자 확인
    public JwtCode validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            // Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // 참고 코드
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException e){
            // 만료된 경우에는 refresh token을 확인하기 위해
            return JwtCode.EXPIRED; // 따로 만들어준 enum
        } catch (JwtException | IllegalArgumentException e) {
            log.info("jwtException : {}", e);
        }
        return JwtCode.DENIED;
    }

    // Request Header에서 Token 값 가져오기
    public String resoleveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // refresh token 발행 및 디비에 저장
    @Transactional
    public String reissueRefreshToken(String refrechToken) throws RuntimeException {
        // refresh token == DB 확인
        Authentication authentication = getAuthentication(refrechToken);
      /*  RefreshToken findRefreshToken = memberService.getMemberByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("userId : " + authentication.getName() + " was not found"));*/


        return null;
    }


}
