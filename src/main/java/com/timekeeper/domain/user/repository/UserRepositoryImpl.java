package com.timekeeper.domain.user.repository;

import com.timekeeper.domain.user.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByRefreshToken(String refreshToken) {
        return userJpaRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public Optional<User> findBySocialId(String socialId) {
        return userJpaRepository.findBySocialId(socialId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
