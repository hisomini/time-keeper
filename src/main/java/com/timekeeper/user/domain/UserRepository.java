package com.timekeeper.user.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    User save(User user);
}
