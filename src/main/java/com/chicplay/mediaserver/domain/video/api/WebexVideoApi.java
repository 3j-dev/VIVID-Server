package com.chicplay.mediaserver.domain.video.api;

import com.chicplay.mediaserver.domain.video.application.WebexVideoService;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebexVideoApi {

    String FILE_URL = "https://nsg1wss.webex.com/nbr/MultiThreadDownloadServlet?siteid=13851897&recordid=194147981&confid=233034994907353810&from=MBS&trackingID=ROUTER_62D57AF1-235C-01BB-4626-0AFE5B9B4626&language=ko_KR&userid=851783062&serviceRecordID=194141046&ticket=SDJTSwAAAAVr4quHo3k%2BpGnatUCRkXRiFLghlF0vFWqnm52tKSD%2FzA%3D%3D&timestamp=1658157810442&islogin=yes&isprevent=no&ispwd=yes";

    @Value("${webex.api.login-uri}")
    private String webexLoginUrl;

    private final AwsS3Service awsS3Service;

    private final WebexVideoService webexVideoService;

    @PostMapping("/api/webex/video")
    @Operation(summary = "webex video 업로드 메소드", description = "webex video를 s3에 업로드하는 api 입니다.")
    @ApiResponse(responseCode = "200", description = "webex video 업로드 완료 후, 상태 코드 200을 반환합니다.")
    public void uploadVideosFromWebex() throws IOException {

        // s3로 업로드
        awsS3Service.uploadRawVideoToS3(FILE_URL);
    }

    @PostMapping("/api/webex/token/{code}")
    @Operation(summary = "webex access token save", description = "webex access token을 get한 후, user entity에 save하는 api입니다.")
    public void saveWebexAccessToken(@PathVariable("code") String code) {

        webexVideoService.saveWebexAccessTokenFromWebexApi(code);

    }

    @GetMapping("/api/login/webex")
    public void redirectWebexLoginUrl(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(webexLoginUrl);
    }
}