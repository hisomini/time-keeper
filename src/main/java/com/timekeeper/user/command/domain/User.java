package com.timekeeper.user.command.domain;

import com.timekeeper.common.domain.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserId id;
    private String email;
    private String password;
    private String name;
    private String position;
    private JoinSource joinSource;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String password, String name, String position,
            JoinSource joinSource) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.position = position;
        this.joinSource = joinSource;
    }

    public static User createUser(String email, String password, String name, String position,
            JoinSource joinSource) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .position(position)
                .joinSource(joinSource)
                .build();
    }
}
