package com.chicplay.mediaserver.domain.video.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoApi {



    @GetMapping("/api/webex/videos")
    public void getWebexRecordingList() {

    }


}




