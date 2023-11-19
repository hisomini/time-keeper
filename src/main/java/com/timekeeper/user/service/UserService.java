package com.timekeeper.user.service;

import com.timekeeper.common.exception.error.BusinessException;
import com.timekeeper.user.domain.JoinSource;
import com.timekeeper.user.domain.User;
import com.timekeeper.user.domain.UserRepository;
import com.timekeeper.user.dto.SignUpDTO;
import com.timekeeper.user.error.UserError;
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

    @Transactional
    public Long signUp(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.findByEmail(signUpDTO.email());
        if (!user.isEmpty()) {
            throw new BusinessException(UserError.USER_ALREADY_EXISTS_ERROR);
        }
        User new_user = User.createUser(
                signUpDTO.email(),
                passwordEncoder.encode(signUpDTO.password()),
                signUpDTO.name(),
                signUpDTO.position(),
                JoinSource.EMAIL
        );
        userRepository.save(new_user);
        return new_user.getId();
    }
}
