package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideosGetRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapShotImageUploadRequest;
import com.chicplay.mediaserver.domain.video.application.VideoService;
import com.chicplay.mediaserver.domain.video.dto.VideoFilePathGetResponse;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapshotImageUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class IndividualVideoApi {

    private final AwsS3Service awsS3Service;

    private final IndividualVideoService individualVideoService;

    private final VideoService videoService;

    @PostMapping(value = "/api/videos/{individual-video-id}/snapshot",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 스냅샷 저장 메소드", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 업로드 완료 후, 각각 이미지의 url을 json 형식으로 반환합니다.")
    public SnapshotImageUploadResponse uploadSnapshotImage(
            @Parameter(description = "multipart/form-data 형식의 이미지를 input으로 받습니다. 이때 key 값은 multipartFile 입니다.")
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @PathVariable("individual-video-id") String individualVideoId,
            @RequestBody @Valid SnapShotImageUploadRequest request
            ) {

        return individualVideoService.uploadSnapshotImage(multipartFile, individualVideoId, request.getVideoTime());
    }

    @Operation(summary = "individual videos list get api", description = "video space participant id를 이용하여 individual video id list를 get 하는 api입니다")
    @GetMapping("/api/videos")
    public List<IndividualVideoGetResponse> read(@RequestBody @Valid IndividualVideosGetRequest individualVideosGetRequest) {

        List<IndividualVideoGetResponse> individualVideoGetResponse = individualVideoService.getByVideoSpaceParticipantId(individualVideosGetRequest.getVideoSpaceParticipantId());

        return individualVideoGetResponse;
    }

    @GetMapping("/api/videos/{individual-video-id}")
    public VideoFilePathGetResponse getFilePath(@PathVariable("individual-video-id") String individualVideoId) throws IOException {

        // individualVideo의 원 video get
        Long videoId = individualVideoService.getVideoById(individualVideoId).getId();

        VideoFilePathGetResponse filePath = videoService.getFilePath(videoId.toString());
        //VideoFilePathGetResponse filePath = videoService.getFilePath(individualVideoId);

        return filePath;
    }

    public void addIndividualVideoToAccount(){

    }



}
