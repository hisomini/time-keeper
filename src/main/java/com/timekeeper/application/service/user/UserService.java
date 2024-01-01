package com.timekeeper.application.service.user;

import com.timekeeper.adapter.in.request.UserPasswordUpdate;
import com.timekeeper.adapter.in.request.UserUpdate;
import com.timekeeper.shared.common.exception.error.BusinessException;
import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.domain.user.JoinSource;
import com.timekeeper.domain.user.User;
import com.timekeeper.domain.user.UserRepository;
import com.timekeeper.adapter.in.request.SignUp;
import com.timekeeper.adapter.in.response.UserInfo;
import com.timekeeper.domain.user.UserError;
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

    public Long signUp(SignUp signUp) {
        Optional<User> user = userRepository.findByEmail(signUp.email());
        if (!user.isEmpty()) {
            throw new BusinessException(UserError.USER_ALREADY_EXISTS_ERROR);
        }
        User new_user = User.builder()
                .name(signUp.name())
                .password(passwordEncoder.encode(signUp.password()))
                .email(signUp.email())
                .position(signUp.position())
                .phoneNumber(signUp.phoneNumber())
                .joinSource(JoinSource.EMAIL).socialId(null)
                .needsProfileUpdate(false)
                .build();
        userRepository.save(new_user);
        return new_user.getId();
    }

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
        userRepository.save(user);
        return tokenMap;
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND_ERROR));
        return new UserInfo(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPosition(),
                user.getPhoneNumber()
        );
    }

    public Long updateUser(UserUpdate userUpdate) {
        User user = userRepository.findByEmail(userUpdate.email())
                .orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND_ERROR));
        user.update(
                userUpdate.name(),
                userUpdate.phoneNumber(),
                userUpdate.position()
        );
        userRepository.save(user);
        return user.getId();
    }

    public Long updateUserPassword(UserPasswordUpdate passwordUpdate) {
        User user = userRepository.findByEmail(passwordUpdate.email())
                .orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND_ERROR));
        if (passwordEncoder.matches(passwordUpdate.beforePassword(), user.getPassword())) {
            user.updatePassword(passwordEncoder.encode(passwordUpdate.afterPassword()));
        } else {
            throw new BusinessException(UserError.PASSWORD_NOT_CORRECT);
        }
        userRepository.save(user);
        return user.getId();
    }

}
