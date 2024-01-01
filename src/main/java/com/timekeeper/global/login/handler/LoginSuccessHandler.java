package com.timekeeper.global.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.shared.common.exception.error.BusinessException;
import com.timekeeper.global.login.service.CustomUserDetails;
import com.timekeeper.domain.user.User;
import com.timekeeper.domain.user.UserRepository;
import com.timekeeper.domain.user.UserError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String email = getUsername(authentication);
        System.out.println("LoginSuccessHandler!!!!!");
        String accessToken = jwtService.createAccessToken(
                email);
        String refreshToken = jwtService.createRefreshToken();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(
                UserError.USER_NOT_FOUND_ERROR));
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("accessToken", accessToken);
        responseMap.put("refreshToken", refreshToken);
        responseMap.put("profileInfo", (user.isNeedsProfileUpdate()) ? "임시계정" : "정식계정");
        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }

    private String getUsername(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getEmail();
    }
}
