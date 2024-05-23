package com.cocoball.demo.service;

import com.cocoball.demo.dto.JoinDTO;
import com.cocoball.demo.entity.UserEntity;
import com.cocoball.demo.repository.UserRepository;
import org.aspectj.weaver.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository _repo;
    private final BCryptPasswordEncoder _bcrypt;

    public UserService(UserRepository repo, BCryptPasswordEncoder bcrypt) {
        _repo = repo;
        _bcrypt = bcrypt;
    }

    // 회원가입 서비스
    public void joinProcess(JoinDTO dto){
        String username = dto.getUsername();
        String password = dto.getPassword();
        int birth = dto.getBirth();
        int avg = dto.getAvg();


        Boolean isExist = _repo.existsByUsername(username);

        if(isExist){
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(_bcrypt.encode(password));
        data.setBirth(birth);
        data.setAvg(avg);

        _repo.save(data);
    }

}
