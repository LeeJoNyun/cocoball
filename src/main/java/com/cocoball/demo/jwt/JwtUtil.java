package com.cocoball.demo.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
// Component어노테이션을 사용하면
// Bean Configuration 파일에 Bean을 따로 등록하지 않아도 사용할 수 있다
public class JwtUtil {
    private final SecretKey key;
    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        // SecretKeySpec 클래스는 특정 비밀 키를 지정된 암호화 알고리즘과 함께 사용할 수 있도록 '표준 키 객체'를 생성한다.
        this.key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8), // 비밀 키를 UTF-8 바이트 배열로 변환
                Jwts.SIG.HS256.key().build().getAlgorithm() // 서명 알고리즘 지정
        );
    }

    // 토큰에서 username값 조회
    public String getUsername(String token){
        return Jwts.parser() // jwt파서 생성
                .verifyWith(key) // 토큰 검증시 사용할 키 설정
                .build() // 설정된 파서에 대해 빌더 패턴을 사용하여 최종 'JwtParser' 객체를 생성한다.
                .parseSignedClaims(token) // 주어진 JWT 토큰을 파싱하고 서명을 검증한다.
                .getPayload() // 파싱된 JWT의 페이로드 부분을 반환한다.
                .get("username", String.class); // 페이로드에서 'username' 클레임 값을 추출하고, String.class를 사용하여 문자열로 변환한다.
    }

    // 생년월일 조회
    public int getBirth(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("birth", Integer.class);
    }
    // 평균에버 조회
    public int getAvg(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("avg", Integer.class);
    }
    // 토큰 생성
    public String createJwt(String username, int birth, int avg, Long expiredMs){
        return Jwts.builder() // JWT생성을 위한 빌더 객체를 생성한다.
                .claim("username", username) // JWT 페이로드에 클레임을 추가
                .claim("birth", birth)
                .claim("avg", avg)
                .issuedAt(new Date(System.currentTimeMillis())) //JWT 발행 시간을 지정한다.
                .expiration(new Date(System.currentTimeMillis() +  expiredMs)) // JWT 만료시간 지정
                .signWith(key) // JWT에 서명을 추가한다.
                .compact(); // 설정이 완료된 JWT를 직렬화 한다.
    }
}
