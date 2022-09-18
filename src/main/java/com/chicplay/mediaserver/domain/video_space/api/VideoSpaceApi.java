package com.chicplay.mediaserver.domain.video_space.api;

import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceGetResponse;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveRequest;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceApi {

    private final VideoSpaceService videoSpaceService;

    @Operation(summary = "video space list get api", description = "로그인한 account의 video space list를 get api 입니다.")
    @GetMapping("/api/video-space-participant")
    public List<VideoSpaceGetResponse> getByAccount() {

        List<VideoSpaceGetResponse> videoSpaceReadRespons = videoSpaceService.read();

        return videoSpaceReadRespons;
    }

    // space 생성 api
    @Operation(summary = "video space create api", description = "video space를 생성하는 api 입니다. 최초 생성시, 생성자만 참가해 있습니다.")
    @PostMapping("/api/video-space")
    public VideoSpaceSaveResponse save(@RequestBody @Valid final VideoSpaceSaveRequest videoSpaceSaveRequest) {
        VideoSpaceSaveResponse videoSpaceSaveResponse = videoSpaceService.save(videoSpaceSaveRequest);
        return videoSpaceSaveResponse;
    }

}
