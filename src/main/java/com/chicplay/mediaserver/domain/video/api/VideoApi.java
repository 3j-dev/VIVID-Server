package com.chicplay.mediaserver.domain.video.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoApi {

    /**
     * 1. 직접 업로드
     * 2. VOD(유튜브) 링크를 통한 업로드
     * 3. 녹강(Webex, Zoom) 공유 링크를 통한 업로드
     *
     */
    @PostMapping("")
    public void save(@RequestPart("multipartFile") MultipartFile multipartFile){

    }



}




