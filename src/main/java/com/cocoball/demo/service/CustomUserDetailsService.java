package com.cocoball.demo.service;

import com.cocoball.demo.dto.CustomUserDetails;
import com.cocoball.demo.entity.UserEntity;
import com.cocoball.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService implements UserDetailsService {
//사용자 인증을 처리하는 class

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에서 회원 정보를 조회한다.
        UserEntity user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with username : " + username);
        }

        return new CustomUserDetails(user);
    }
}
