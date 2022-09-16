package com.chicplay.mediaserver.domain.video_space.api;

import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveRequest;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceApi {

    private final VideoSpaceService videoSpaceService;

    // space 생성 api
    @PostMapping("/api/video-space")
    public VideoSpaceSaveResponse save(@RequestBody @Valid final VideoSpaceSaveRequest videoSpaceSaveRequest) {
        VideoSpaceSaveResponse videoSpaceSaveResponse = videoSpaceService.save(videoSpaceSaveRequest);
        return videoSpaceSaveResponse;
    }

}
