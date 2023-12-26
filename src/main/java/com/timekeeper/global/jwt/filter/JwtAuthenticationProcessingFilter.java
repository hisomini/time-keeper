package com.timekeeper.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timekeeper.global.jwt.exception.ErrorResponse;
import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.global.login.service.CustomUserDetails;
import com.timekeeper.global.login.service.LoginService;
import com.timekeeper.user.domain.User;
import com.timekeeper.user.domain.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        System.out.println("혹시 여기 타니?");
        String accessToken = jwtService.getAccessToken(request);
        System.out.println(request.getRequestURI());
        try {
            if (request.getRequestURI().equals("/users/login") || request.getRequestURI()
                    .equals("/users/refresh") || request.getRequestURI().equals("/users/signup")) {
                filterChain.doFilter(request, response);
            } else if (StringUtils.hasText(accessToken) && jwtService.validateToken(accessToken)) {
                String email = jwtService.getEmail(accessToken);
                Optional<User> user = userRepository.findByEmail(email);
                if (user.isPresent()) {
                    saveAuthentication(user.get());
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("utf-8");
                    ErrorResponse errorResponse = new ErrorResponse(400, "사용자 정보가 존재하지 않습니다.");
                    new ObjectMapper().writeValue(response.getWriter(), errorResponse);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ErrorResponse errorResponse = new ErrorResponse(400, "토큰이 존재하지 않거나 유효하지 않습니다.");
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        }

    }


    public void saveAuthentication(User myUser) {
        String password = myUser.getPassword();
        if (password == null) {
            password = "my-project-time-key-1111";
        }
        CustomUserDetails userDetails = loginService.loadUserByUsername(myUser.getEmail());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
