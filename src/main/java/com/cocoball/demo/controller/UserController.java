package com.cocoball.demo.controller;

import com.cocoball.demo.dto.JoinDTO;
import com.cocoball.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService service;
    public UserController(UserService service){
        this.service = service;
    }


    @PostMapping("/user/join")
    public ResponseEntity<String> join(@Valid JoinDTO dto){
        service.joinProcess(dto);
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@Valid JoinDTO dto){
        return ResponseEntity.ok().body("hi");
    }


}
