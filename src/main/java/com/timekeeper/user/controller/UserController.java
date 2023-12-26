package com.timekeeper.user.controller;

import com.timekeeper.global.common.exception.error.BusinessException;
import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.user.dto.SignUpDTO;
import com.timekeeper.user.error.UserError;
import com.timekeeper.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/users/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 되었습니다");
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("redirect_url 접근했습니다");
        return "안녕하세요";
    }

    //리프레시-> refresh token 유효 -> accesstoken 재 발급, 1. refresh token 기한 하루 남은경우 재발급해서 같이 리턴, refresh token이 이미 지난경우 재로그인 return
    @GetMapping("/users/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request) {
        String refreshToken = jwtService.getRefreshToken(request);
        if (refreshToken == null) {
            throw new BusinessException(UserError.TOKEN_NOT_VALID);
        }
        Map<String, String> tokens = userService.refresh(refreshToken);
        return ResponseEntity.ok(tokens);
    }
}
