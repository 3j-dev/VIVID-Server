package com.chicplay.mediaserver.global.auth;

import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.domain.user.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .build();

        // 최초 로그인이라면 회원가입 처리.
        if (!userService.existsByEmail(userLoginRequest.getEmail())) {
            userService.signUp(userLoginRequest);
        }

        UserJwt userJwt = jwtProvider.generateToken(userLoginRequest.getEmail(), Role.USER.name());



        writeTokenResponse(response, userJwt);
    }

    private void writeTokenResponse(HttpServletResponse response, UserJwt userJwt) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", userJwt.getToken());
        response.addHeader("Refresh", userJwt.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(userJwt));
        writer.flush();
    }
}
