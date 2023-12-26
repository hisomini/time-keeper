package com.timekeeper.user.service;

import com.timekeeper.global.common.exception.error.BusinessException;
import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.user.domain.JoinSource;
import com.timekeeper.user.domain.User;
import com.timekeeper.user.domain.UserRepository;
import com.timekeeper.user.dto.SignUpDTO;
import com.timekeeper.user.error.UserError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public Long signUp(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.findByEmail(signUpDTO.email());
        if (!user.isEmpty()) {
            throw new BusinessException(UserError.USER_ALREADY_EXISTS_ERROR);
        }
        User new_user = User.builder()
                .name(signUpDTO.name())
                .password(passwordEncoder.encode(signUpDTO.password()))
                .email(signUpDTO.email())
                .position(signUpDTO.position())
                .phoneNumber(signUpDTO.phoneNumber())
                .joinSource(JoinSource.EMAIL).socialId(null)
                .needsProfileUpdate(false)
                .build();
        userRepository.save(new_user);
        return new_user.getId();
    }

    @Transactional
    public Map<String, String> refresh(String refreshToken) {
        if (!jwtService.validateToken(refreshToken)) {
            throw new BusinessException(UserError.TOKEN_NOT_VALID);
        }
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND_ERROR));
        long now = System.currentTimeMillis();
        String accessToken = jwtService.createAccessToken(user.getEmail());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(jwtService.getAccessHeader(), accessToken);
        Claims claims = Jwts.parser().setSigningKey(jwtService.getSecretKey())
                .parseClaimsJws(refreshToken).getBody();

        long refreshTokenExpiration = claims.getExpiration().getTime();
        long diffDays = (refreshTokenExpiration - now) / 1000 / (24 * 3600);
        if (diffDays <= 2) {
            String newRefreshToken = jwtService.createRefreshToken();
            user.updateRefreshToken(newRefreshToken);
            tokenMap.put(jwtService.getRefreshHeader(), newRefreshToken);
        }
        return tokenMap;
    }
}
