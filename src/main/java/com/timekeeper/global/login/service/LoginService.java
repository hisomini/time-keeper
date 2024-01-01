package com.timekeeper.global.login.service;

import com.timekeeper.domain.user.User;
import com.timekeeper.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;


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
        return customUserDetails;
    }
}
