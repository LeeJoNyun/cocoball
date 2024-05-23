package com.cocoball.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    // UsernamePasswordAuthenticationFilter 상속 받아서 선언하는 이유
    // 기본적으로 사용자 이름과 비밀번호 기반의 인증을 처리한다.
    // 인증 성공 후 JWT 토큰을 발행하여 클라이언트에게 반환하는 로직을 추가 할 수 있다.

    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request); // obtainUsername은 Spring Security에서 사용자가 제공한 인증 정보에서 사용자 이름을 추출한다
        String password = obtainPassword(request);
        System.out.println(username);
        // 넘어온 정보를 검증하기 위해 token에 담는다
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // AuthenticationManager에 token 을 전달(Spring Security에서 검증한다)
        return authenticationManager.authenticate(token);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("success");
    }
}
