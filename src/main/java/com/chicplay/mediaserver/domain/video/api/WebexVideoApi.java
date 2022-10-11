package com.chicplay.mediaserver.domain.video.api;

import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebexVideoApi {

    String FILE_URL = "https://nsg1wss.webex.com/nbr/MultiThreadDownloadServlet?siteid=13851897&recordid=194147981&confid=233034994907353810&from=MBS&trackingID=ROUTER_62D57AF1-235C-01BB-4626-0AFE5B9B4626&language=ko_KR&userid=851783062&serviceRecordID=194141046&ticket=SDJTSwAAAAVr4quHo3k%2BpGnatUCRkXRiFLghlF0vFWqnm52tKSD%2FzA%3D%3D&timestamp=1658157810442&islogin=yes&isprevent=no&ispwd=yes";

    private final AwsS3Service awsS3Service;

    @PostMapping("/api/webex/video")
    @Operation(summary = "webex video 업로드 메소드", description = "webex video를 s3에 업로드하는 메소드 입니다.")
    @ApiResponse(responseCode = "200", description = "webex video 업로드 완료 후, 상태 코드 200을 반환합니다.")
    public void uploadVideosFromWebex() throws IOException {

        // s3로 업로드
        awsS3Service.uploadRawVideoToS3(FILE_URL);
    }

    public void getWebexRecordings() {

    }
}