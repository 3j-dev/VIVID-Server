package com.chicplay.mediaserver.domain.video.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoApi {


    @GetMapping("/{video-id}/visualIndexImages")
    public void getVisualIndexImages(@PathVariable("video-id") String videoId){

    }

}




