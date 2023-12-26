package com.timekeeper.user.repository;

import com.timekeeper.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findBySocialId(String socialId);
}
