package com.chicplay.mediaserver.domain.video.api;

import com.chicplay.mediaserver.domain.video.application.S3Service;

import com.chicplay.mediaserver.domain.video.domain.SnapshotImage;
import com.chicplay.mediaserver.domain.video.dto.UploadSnapshotImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoController {

    String FILE_URL = "https://nsg1wss.webex.com/nbr/MultiThreadDownloadServlet?siteid=13851897&recordid=194147981&confid=233034994907353810&from=MBS&trackingID=ROUTER_62D57AF1-235C-01BB-4626-0AFE5B9B4626&language=ko_KR&userid=851783062&serviceRecordID=194141046&ticket=SDJTSwAAAAVr4quHo3k%2BpGnatUCRkXRiFLghlF0vFWqnm52tKSD%2FzA%3D%3D&timestamp=1658157810442&islogin=yes&isprevent=no&ispwd=yes";

    private final S3Service s3Service;

    @GetMapping("/api/webex/videos")
    public void getWebexRecordingList() {

    }


    @PostMapping("/api/webex/video")
    public void uploadVideosFromWebex() throws IOException {

        // s3로 업로드
        s3Service.uploadRawVideoToS3(FILE_URL);
    }

    @PostMapping(value = "/api/img/snapshot",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 스냅샷 저장 메소드", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponse(
            responseCode = "200",
            description = "이미지 업로드 완료 후, 각각 이미지의 url list를 json 형식으로 반환합니다.")
    public List<UploadSnapshotImageResponse> uploadImageSnapshots(
            @Parameter(
                    description = "multipart/form-data 형식의 이미지 리스트를 input으로 받습니다. 이때 key 값은 multipartFile 입니다.",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart("multipartFile") List<MultipartFile> multipartFile) {
        return s3Service.uploadSnapshotImagesToS3(multipartFile, "test01");
    }

}
