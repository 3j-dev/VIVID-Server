package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserAuthTokenDao;
import com.chicplay.mediaserver.domain.user.dto.OAuthAttributes;
import com.chicplay.mediaserver.domain.user.domain.Role;
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

    public String getRefreshTokenFromEmail(String email) {

        String refreshToken = userAuthTokenDao.getRefreshToken(email);

        return refreshToken;
    }

//    // 유저 생성 및 수정 서비스 로직
//    private User saveOrUpdate(OAuthAttributes attributes){
//        User user = userRepository.findByEmail(attributes.getEmail())
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }
}
