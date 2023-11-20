package com.timekeeper.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        int statusCode;
        if (exception instanceof BadCredentialsException) {
            errorMessage = UserAuthenticationError.USER_INFO_NOT_CORRECT.getMessage();
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else {
            errorMessage = UserAuthenticationError.INTERNAL_SERVER_ERROR.getMessage();
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().println(mapper.writeValueAsString(errorMessage));
    }
}
