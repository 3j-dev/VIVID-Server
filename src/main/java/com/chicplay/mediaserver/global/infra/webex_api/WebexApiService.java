package com.chicplay.mediaserver.global.infra.webex_api;

import com.chicplay.mediaserver.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebexApiService {

    @Value("${webex.api.client-id}")
    private String clientId;

    @Value("${webex.api.client-secret}")
    private String clientSecret;

    @Value("${webex.api.redirect-uri}")
    private String redirectUri;

    @Qualifier("webexRestTemplate")
    private final RestTemplate webexRestTemplate;

    // 최초 로그인 시,
    public String getAccessToken(String code) {

        // http body 설정
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("code", code);
        parameters.add("redirect_uri", redirectUri);

        // http header 설정.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity formEntity = new HttpEntity<>(parameters, headers);

        // webex api 호출
        ResponseEntity<Map> response = webexRestTemplate.postForEntity("/access_token", formEntity, Map.class);

        // access token get
        String accessToken = response.getBody().get("access_token").toString();

        return accessToken;
    }





}
