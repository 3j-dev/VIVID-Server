package com.chicplay.mediaserver.domain.video_space.api;

import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.dto.HostedVideoSpaceGetResponse;
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

    @Operation(summary = "video space list get api", description = "로그인한 user의 video space list를 get api 입니다.")
    @GetMapping("/api/video-space")
    public List<VideoSpaceGetResponse> getByUser() {

        List<VideoSpaceGetResponse> videoSpaceReadResponse = videoSpaceService.getList();

        return videoSpaceReadResponse;
    }

    @Operation(summary = "video space hosted list get api", description = "로그인한 user가 host인 video space list를 get api 입니다.")
    @GetMapping("/api/video-space/hosted")
    public List<HostedVideoSpaceGetResponse> getHostedVideoSpaces(){

        List<HostedVideoSpaceGetResponse> hostedVideoSpace = videoSpaceService.getHostedList();

        return hostedVideoSpace;
    }


    // space 생성 api
    @Operation(summary = "video space create api", description = "video space를 생성하는 api 입니다. 최초 생성시, 생성자만 참가해 있습니다.")
    @PostMapping("/api/video-space")
    public VideoSpaceSaveResponse save(@RequestBody @Valid final VideoSpaceSaveRequest videoSpaceSaveRequest) {
        VideoSpaceSaveResponse videoSpaceSaveResponse = videoSpaceService.save(videoSpaceSaveRequest);
        return videoSpaceSaveResponse;
    }

}
