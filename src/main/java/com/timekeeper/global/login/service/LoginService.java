package com.timekeeper.global.login.service;

import com.timekeeper.domain.user.User;
import com.timekeeper.domain.user.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;
    Set<String> adminPositions = new HashSet<>(Arrays.asList("부장", "차장", "이사", "사장"));

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("====loadUserByUsername=====");
        System.out.println(user);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(user.get().getId());
        customUserDetails.setEmail(user.get().getEmail());
        customUserDetails.setPassword(user.get().getPassword());
        String position = user.get().getPosition();
        if (adminPositions.contains(position)) {
            customUserDetails.setAuthorities("ADMIN");
        } else {
            customUserDetails.setAuthorities("USER");
        }
        return customUserDetails;
    }
}
