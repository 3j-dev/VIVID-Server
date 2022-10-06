package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserAuthTokenDao;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.global.auth.JwtProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpHeaders;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProviderService jwtProviderService;
    private final HttpSession httpSession;
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

        UserAuthToken userAuthToken = jwtProviderService.generateToken(userLoginRequest.getEmail(), Role.USER.name());

        // then, token 발급 후 -> access token, refresh token
        //writeTokenResponse(response, userAuthToken);'

        String targetUrl = UriComponentsBuilder.fromUriString("https://dev.edu-vivid.com")
                .queryParam("token", userAuthToken.getToken())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        // redis - refresh token save
        //userAuthTokenDao.saveRefreshToken(userAuthToken.getEmail(), userAuthToken.getRefreshToken());
        httpSession.setAttribute("refreshToken", userAuthToken.getRefreshToken());
    }

//    private void writeTokenResponse(HttpServletResponse response, UserAuthToken userAuthToken) throws IOException {
//
//        response.setContentType("text/html;charset=UTF-8");
//
//        response.addHeader("Auth", userAuthToken.getToken());
//        //response.addHeader("Refresh", userAuthToken.getRefreshToken());
//        response.setContentType("application/json;charset=UTF-8");
//
//        PrintWriter writer = response.getWriter();
//        writer.println(objectMapper.writeValueAsString(userAuthToken));
//        writer.flush();
//    }
}
