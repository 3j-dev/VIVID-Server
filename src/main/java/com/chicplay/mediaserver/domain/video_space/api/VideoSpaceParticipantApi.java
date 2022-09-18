package com.chicplay.mediaserver.domain.video_space.api;

import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceParticipantService;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceParticipantSaveRequest;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceParticipantSaveResponse;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceParticipantApi {

    private final VideoSpaceParticipantService videoSpaceParticipantService;

    // video space에 account를 추가한다. 즉, VideoSpaceParticipant save
    @Operation(summary = "video space에 account를 추가하는 api", description = "video space에 account 추가하는 api 입니다.")
    @PostMapping("/api/video-space-participant")
    public VideoSpaceParticipantSaveResponse save(@RequestBody @Valid final VideoSpaceParticipantSaveRequest videoSpaceParticipantSaveRequest) {

        VideoSpaceParticipantSaveResponse videoSpaceParticipantSaveResponse = videoSpaceParticipantService.save(videoSpaceParticipantSaveRequest);

        return videoSpaceParticipantSaveResponse;
    }

}
