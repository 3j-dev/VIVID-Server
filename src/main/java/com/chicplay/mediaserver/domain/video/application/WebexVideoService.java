package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.video.api.WebexVideoApi;
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

    public void loginWebex() {

        // access token이 존재하지 않을 경우, throw -> login page로 이동하게끔
        if (!userService.existsWebexAccessToken()) {

        }





    }
}
