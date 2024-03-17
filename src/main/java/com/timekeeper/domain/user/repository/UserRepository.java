package com.timekeeper.domain.user.repository;

import com.timekeeper.domain.user.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findBySocialId(String socialId);

    User save(User user);
}
