package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserAuthTokenDao;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.global.auth.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final UserAuthTokenDao userAuthTokenDao;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .build();

        // 최초 로그인이라면 회원가입 처리.
        if (!userService.existsByEmail(userLoginRequest.getEmail())) {
            userService.signUp(userLoginRequest);
        }

        UserAuthToken userAuthToken = jwtProvider.generateToken(userLoginRequest.getEmail(), Role.USER.name());

        // then, token 발급 후 -> access token, refresh token
        writeTokenResponse(response, userAuthToken);

        // redis - refresh token save
        userAuthTokenDao.saveRefreshToken(userLoginRequest.getEmail(), userAuthToken.getRefreshToken());
    }

    private void writeTokenResponse(HttpServletResponse response, UserAuthToken userAuthToken) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", userAuthToken.getToken());
        response.addHeader("Refresh", userAuthToken.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(userAuthToken));
        writer.flush();
    }
}
