package com.timekeeper.global.oauth2.service;

import com.timekeeper.global.oauth2.CustomOauth2User;
import com.timekeeper.global.oauth2.OAuthAttributes;
import com.timekeeper.domain.user.User;
import com.timekeeper.domain.user.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(registrationId,
                userNameAttributeName, attributes);
        //email 중복가입 안됨, 한 종류의 socialId로 회원가입한 경우 다른 socialId로 회원가입 불가능
        User createdUser = getUser(extractAttributes);
        return new CustomOauth2User(
                null,
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getPosition(),
                createdUser.isNeedsProfileUpdate()
        );
    }

    private User getUser(OAuthAttributes attributes) {
        User user = userRepository.findBySocialId(
                attributes.getSocialId()).orElse(null);

        if (user == null) {
            return saveUser(attributes);
        }
        return user;
    }

    /**
     * OAuthAttributes의 toEntity() 메소드를 통해 빌더로 User 객체 생성 후 반환 생성된 User 객체를 DB에 저장 : socialType,
     * socialId, email, role 값만 있는 상태
     */
    private User saveUser(OAuthAttributes attributes) {
        User createdUser = attributes.toEntity();
        return userRepository.save(createdUser);
    }
}
