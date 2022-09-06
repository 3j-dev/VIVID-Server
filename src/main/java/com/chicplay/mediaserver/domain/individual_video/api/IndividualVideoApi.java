package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideosGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapShotImageUploadRequest;
import com.chicplay.mediaserver.global.infra.storage.S3Service;
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
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class IndividualVideoApi {

    private final S3Service s3Service;

    private final IndividualVideoService individualVideoService;

    @PostMapping(value = "/{individual-video-id}/snapshot",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 스냅샷 저장 메소드", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 업로드 완료 후, 각각 이미지의 url을 json 형식으로 반환합니다.")
    public SnapshotImageUploadResponse uploadImageSnapshots(
            @Parameter(description = "multipart/form-data 형식의 이미지를 input으로 받습니다. 이때 key 값은 multipartFile 입니다.")
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @PathVariable("individual-video-id") String individualVideoId,
            @RequestBody @Valid SnapShotImageUploadRequest request
            ) {

        return s3Service.uploadSnapshotImagesToS3(multipartFile, individualVideoId, request.getVideoTime());
    }

    @GetMapping("")
    public List<IndividualVideosGetResponse> getList() {

        // get id, id 꺼내오는 방식 협의 필요. 테스팅용
        String id = "test01";

        // userId를 통해 individualVideoList get
        List<IndividualVideosGetResponse> individualVideoList = individualVideoService.getListByUser(id);

        return individualVideoList;
    }



}
