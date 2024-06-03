package com.cocoball.demo.jwt;

import com.cocoball.demo.dto.CustomUserDetails;
import com.cocoball.demo.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            // 다음으로 넘김
            filterChain.doFilter(request,response);
            // 메서드 종료
            return;
        }

        String token = authorization.split(" ")[1];

        // 토큰 만료 확인
        if(jwtUtil.isExpired(token)){
            System.out.println("token is expired");
            filterChain.doFilter(request,response);

            return;
        }

        String username = jwtUtil.getUsername(token);

        //userEntity 생성 값 추가
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        //DB를 자주 조회하는 것을 방지
        entity.setPassword("temppassword");

        CustomUserDetails details = new CustomUserDetails(entity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
