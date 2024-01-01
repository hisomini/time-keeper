package com.timekeeper.global.oauth2;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter
public class CustomOauth2User extends DefaultOAuth2User {

    private String email;
    private String position;
    private boolean needsProfileUpdate;

    public CustomOauth2User(
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attributes, String nameAttributeKey,
            String email, String position, boolean needsProfileUpdate) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.position = position;
        this.needsProfileUpdate = needsProfileUpdate;
    }
}
