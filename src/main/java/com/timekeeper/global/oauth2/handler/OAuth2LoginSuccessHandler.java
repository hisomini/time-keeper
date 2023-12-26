package com.timekeeper.global.oauth2.handler;

import com.timekeeper.global.jwt.service.JwtService;
import com.timekeeper.global.oauth2.CustomOauth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            System.out.println(request);
            CustomOauth2User oAuth2User = (CustomOauth2User) authentication.getPrincipal();
            System.out.println(oAuth2User.isNeedsProfileUpdate());
            loginSuccess(response, oAuth2User);
            String accessToken =
                    "Bearer " + jwtService.createAccessToken(oAuth2User.getEmail());
            String targetUrl = UriComponentsBuilder.fromUriString(
                            "https://b2tech.co.kr/loginSuccess")
                    .queryParam("accessToken", accessToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//            if (oAuth2User.isNeedsProfileUpdate()) {
//                System.out.println("--------여기 탔니이");
//                String accessToken =
//                        "Bearer " + jwtService.createAccessToken(oAuth2User.getEmail());
//                System.out.println(accessToken);
//                response.setHeader(jwtService.getAccessHeader(), accessToken);
//                response.sendRedirect("/test");
//                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
//            } else {
//            }
        } catch (Exception e) {
            throw e;
        }

    }

    private void loginSuccess(HttpServletResponse response, CustomOauth2User oAuth2User)
            throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
