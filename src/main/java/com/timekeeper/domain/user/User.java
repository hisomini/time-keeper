package com.timekeeper.domain.user;

import com.timekeeper.shared.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String position;
    @NotNull
    private JoinSource joinSource;
    private String socialId;
    private String refreshToken;
    @NotNull
    private boolean needsProfileUpdate;
    private String profile;

    @Builder
    private User(String email, String password, String name, String phoneNumber, String position,
            JoinSource joinSource, String socialId, boolean needsProfileUpdate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.joinSource = joinSource;
        this.socialId = socialId;
        this.needsProfileUpdate = needsProfileUpdate;
    }


    public void update(String name, String phoneNumber, String position) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.needsProfileUpdate = false;

    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void uploadProfile(String url) {
        this.profile = url;
    }
}
