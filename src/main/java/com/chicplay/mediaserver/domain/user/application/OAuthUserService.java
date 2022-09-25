package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserAuthTokenDao;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.dto.OAuthAttributes;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.dto.UserNewTokenReqeust;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenExpiredException;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenNotFoundException;
import com.chicplay.mediaserver.global.auth.JwtProviderService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserAuthTokenDao userAuthTokenDao;

    private final JwtProviderService jwtProviderService;

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

    public UserNewTokenReqeust getNewAccessTokenFromEmail(String email) {

        // get refresh token from redis
        String refreshToken = userAuthTokenDao.getRefreshToken(email);

        try {
            // 유효한 토큰 일 경우 re-issue access token return
            if (jwtProviderService.validateToken(refreshToken)) {
                UserAuthToken userAuthToken = jwtProviderService.generateToken(email, Role.USER.name());

                return UserNewTokenReqeust.builder().accessToken(userAuthToken.getToken()).build();
            }
        } catch (ExpiredJwtException expiredJwtException){

            // refresh token 만료 된 경우, logout 시킨다.
            throw new RefreshTokenExpiredException();
        }

        throw new RefreshTokenNotFoundException();
    }

//    // 유저 생성 및 수정 서비스 로직
//    private User saveOrUpdate(OAuthAttributes attributes){
//        User user = userRepository.findByEmail(attributes.getEmail())
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }
}
