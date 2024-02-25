package com.timekeeper.adapter.in;

import com.timekeeper.adapter.in.request.UserPasswordUpdate;
import com.timekeeper.adapter.in.request.UserUpdate;
import com.timekeeper.shared.common.exception.error.BusinessException;
import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.global.login.service.CustomUserDetails;
import com.timekeeper.adapter.in.request.SignUp;
import com.timekeeper.adapter.in.response.UserInfo;
import com.timekeeper.domain.user.UserError;
import com.timekeeper.application.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUp signUp) {
        userService.signUp(signUp);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 되었습니다");
    }

    @GetMapping
    public UserInfo getUserInfo(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserInfo(user.getId());
    }

    //리프레시-> refresh token 유효 -> accesstoken 재 발급, 1. refresh token 기한 하루 남은경우 재발급해서 같이 리턴, refresh token이 이미 지난경우 재로그인 return
    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request) {
        String refreshToken = jwtService.getRefreshToken(request);
        if (refreshToken == null) {
            throw new BusinessException(UserError.TOKEN_NOT_VALID);
        }
        Map<String, String> tokens = userService.refresh(refreshToken);
        return ResponseEntity.ok(tokens);
    }

    @PatchMapping
    public ResponseEntity<String> updateUser(
            @RequestBody UserUpdate userUpdate) {
        userService.updateUser(userUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("회원정보가 수정되었습니다.");
    }

    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody UserPasswordUpdate passwordUpdate) {
        userService.updateUserPassword(passwordUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경되었습니다.");
    }
}
