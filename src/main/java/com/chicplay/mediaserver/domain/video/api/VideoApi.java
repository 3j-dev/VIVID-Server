package com.chicplay.mediaserver.domain.video.api;

import com.chicplay.mediaserver.domain.individual_video.dto.SnapShotImageUploadRequest;
import com.chicplay.mediaserver.domain.video.application.VideoService;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveRequest;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoApi {

    private final VideoService videoService;

    /**
     * 1. 직접 업로드
     * 2. VOD(유튜브) 링크를 통한 업로드
     * 3. 녹강(Webex, Zoom) 공유 링크를 통한 업로드
     */

    /***
     * raw-video-storage에 video를 업로드하는 api
     * aws media convert를 이용한
     * 인코딩이 완료되면 lambda 함수를 통해서 자동으로 save api 호출
     */
    @PostMapping(value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "video 직접 업로드 api", description = "video를 직접 업로드하는 api")
    public VideoSaveResponse upload(
            @RequestPart("video")
            @Parameter(description = "multipartFile video file")
            MultipartFile multipartFile,

            @RequestPart("videoInfo")
            @Parameter(description = "VideoSaveRequest 객체 json input")
            @Valid final VideoSaveRequest videoSaveRequest
    ) {

        VideoSaveResponse videoSaveResponse = videoService.upload(multipartFile, videoSaveRequest);

        return videoSaveResponse;
    }

    @Operation(summary = "video의 업로드 상태를 변환 api", description = "video의 업로드 상태를 true로 바꾸는 api 입니다. 해당 api는 aws 람다에서 호출됩니다.")
    @PutMapping(value = "/{videoId}/uploaded")
    public void changeUploadStateAfterUploaded(@PathVariable("videoId") Long videoId) {

        // upload가 완료된후 uploaded 상태 변경
        videoService.changeUploadState(videoId, true);

    }

}




