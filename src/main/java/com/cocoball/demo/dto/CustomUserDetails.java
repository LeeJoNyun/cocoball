package com.cocoball.demo.dto;

import com.cocoball.demo.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 'UserDetails'를 인터페이스로 사용하는 이유
// Spring Security에서 사용자 정보를 인증 및 권한 부여의 목적으로 '표준화된 방식' 으로 사용하기 위해서
// 사용자 정보를 제공하는 메서드들을 정의하고 있다.
public class CustomUserDetails implements UserDetails {
    private final UserEntity userEntity;
    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한 반환
        // 사용 안함
       return null;
    }

    @Override
    public String getPassword() {
        // 사용자 암호 반환
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        // 사용자 이름 반환
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 사용자 계정의 만료 여부 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 사용자 계정이 잠겼는지 여부 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자 자격 증명이 만료되었는지 여부 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 사용자 활성화 여부 반환
        return true;
    }

    //사용자 생년월일 반환
    public int getBirth(){
        return userEntity.getBirth();
    }
    //사용자 에버 반환
    public int getAvg() {
        return userEntity.getAvg();
    }

}
