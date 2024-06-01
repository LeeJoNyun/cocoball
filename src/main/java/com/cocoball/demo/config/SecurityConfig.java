package com.cocoball.demo.config;

import com.cocoball.demo.filter.LoginFilter;
import com.cocoball.demo.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;
    private final JwtUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration configuration, JwtUtil jwtUtil) {
        this.configuration = configuration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        //비밀번호를 암호화하는 데 사용할 수 있는 메서드
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        // AuthenticationManager 빈을 정의하는 이유
        // Spring Security가 기본적으로 제공하는 인증 매니저를 사용
        // 이미 구성된 다른 Spring Security 구성 요소에서 사용 중인 인증 매니저를 재사용(LoginFilter)
        // AuthenticationConfiguration는 Spring Security가 제공하는 인증 구성을 관리하는 클래스
        return configuration.getAuthenticationManager();
        // getAuthenticationManager() 메서드는 해당 구성에서 사용 중인 인증 매니저를 반환
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // formLogin x -> JWT 인증방식 사용
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        reg -> reg.requestMatchers("*/join","*/login").permitAll()
                                .anyRequest().authenticated()

                )
                .addFilterAt(new LoginFilter(authenticationManager(configuration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // UsernamePasswordAuthenticationFilter자리에 LoginFilter를 사용하겠다는 의미
//                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
                .sessionManagement(
                        conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
