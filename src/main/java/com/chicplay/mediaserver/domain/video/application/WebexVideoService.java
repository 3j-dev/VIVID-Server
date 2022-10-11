package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.domain.Institution;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.global.infra.webex_api.WebexApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class WebexVideoService {

    private final UserService userService;

    private final WebexApiService webexApiService;


    // get recordings
    public void getRecordings() {

        // access token get
        String webexAccessToken = userService.getWebexAccessToken();



    }

    // webex api를 이용해서 access token을 얻어내고, save합니다.
    public void saveWebexAccessTokenFromWebexApi(String code) {

        // user get
        User user = userService.findByAccessToken();

        // access token get
        String accessToken = webexApiService.getAccessToken(code);

        // access token save
        Institution institution = user.getInstitution();
        institution.changeWebexAccessToken(accessToken);

        user.changeInstitution(institution);
    }

    public void loginWebex() {


    }
}
