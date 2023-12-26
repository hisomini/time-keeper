package com.timekeeper.global.oauth2;

import com.timekeeper.user.domain.JoinSource;
import com.timekeeper.user.domain.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private String email;
    private String name;
    private String phoneNumber;
    private String socialId;
    private JoinSource joinSource;

    @Builder
    private OAuthAttributes(String nameAttributeKey, String email, String name, String phoneNumber,
            String socialId, JoinSource joinSource) {
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.socialId = socialId;
        this.joinSource = joinSource;
    }

    public static OAuthAttributes of(String registrationId, String nameAttributeKey,
            Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver(nameAttributeKey, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofNaver(String nameAttributeKey,
            Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println(JoinSource.NAVER);
        System.out.println(response);
        return OAuthAttributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .socialId((String) response.get("id"))
                .phoneNumber((String) response.get("mobile"))
                .joinSource(JoinSource.NAVER)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(null)
                .phoneNumber(phoneNumber)
                .needsProfileUpdate(true)
                .socialId(socialId)
                .position("임시계정")
                .joinSource(joinSource)
                .build();
    }

}
