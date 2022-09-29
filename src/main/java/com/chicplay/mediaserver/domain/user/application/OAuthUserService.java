package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserAuthTokenDao;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.dto.OAuthAttributes;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.domain.user.dto.UserNewTokenReqeust;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenExpiredException;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenNotFoundException;
import com.chicplay.mediaserver.global.auth.JwtProviderService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserAuthTokenDao userAuthTokenDao;

    private final JwtProviderService jwtProviderService;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(oAuth2User.getAttributes(), userNameAttributeName);

        Map<String, Object> attributeMap = oAuthAttributes.convertToMap();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.name())),attributeMap,"email");
    }

    // access token re-issue
    public UserNewTokenReqeust getNewAccessToken() {

        UserAuthToken tokenFromRedisSession = jwtProviderService.getTokenFromRedisSession();

        return UserNewTokenReqeust.builder().accessToken(tokenFromRedisSession.getToken()).build();
    }

    // access token re-isuue from silent refresh
    public UserNewTokenReqeust getNewAccessTokenFromSilentRefresh(HttpServletRequest request) {

        String token = jwtProviderService.parseBearerToken(request);

        // silent refresh 시, access token 검사.
        if (!StringUtils.hasText(token) || !jwtProviderService.validateToken(token)) {
            throw new JwtException("Access Token Invalid");
        }

        UserAuthToken tokenFromRedisSession = jwtProviderService.getTokenFromRedisSession();

        return UserNewTokenReqeust.builder().accessToken(tokenFromRedisSession.getToken()).build();
    }

    public void removeRefreshTokenByLogout(HttpServletRequest httpRequest) {

        // email from access token
        String accessToken = jwtProviderService.parseBearerToken(httpRequest);
        String email = jwtProviderService.getEmail(accessToken);

        // remove refresh token
        userAuthTokenDao.removeRefreshToken(email);

    }

//    // 유저 생성 및 수정 서비스 로직
//    private User saveOrUpdate(OAuthAttributes attributes){
//        User user = userRepository.findByEmail(attributes.getEmail())
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }
}
