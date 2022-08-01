package com.chicplay.mediaserver.domain.video.api;

import com.chicplay.mediaserver.domain.video.application.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void getWebexRecordingList(){

    }


    @PostMapping("/api/webex/video")
    public void uploadVideosFromWebex() throws IOException {

        // s3로 업로드
        s3Service.uploadRawVideoToS3(FILE_URL);
    }

    @PostMapping("/api/img/snapshot")
    public List<String> uploadImageSnapshots(@RequestPart List<MultipartFile> multipartFile){
        return s3Service.uploadSnapshotImagesToS3(multipartFile,"test01");
    }

}
