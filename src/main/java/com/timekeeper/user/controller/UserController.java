package com.timekeeper.user.controller;

import com.timekeeper.user.dto.SignUpDTO;
import com.timekeeper.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 되었습니다");
    }

}
