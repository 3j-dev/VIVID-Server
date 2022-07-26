package com.chicplay.mediaserver.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.chicplay.mediaserver.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static org.hibernate.boot.archive.internal.ArchiveHelper.getBytesFromInputStream;



@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoController {

    String FILE_URL = "https://nsg1wss.webex.com/nbr/MultiThreadDownloadServlet?siteid=13851897&recordid=194147981&confid=233034994907353810&from=MBS&trackingID=ROUTER_62D57AF1-235C-01BB-4626-0AFE5B9B4626&language=ko_KR&userid=851783062&serviceRecordID=194141046&ticket=SDJTSwAAAAVr4quHo3k%2BpGnatUCRkXRiFLghlF0vFWqnm52tKSD%2FzA%3D%3D&timestamp=1658157810442&islogin=yes&isprevent=no&ispwd=yes";

    private final S3Service s3Service;

    @GetMapping("")
    public String test(){

        return "test";
    }

    @GetMapping("/api/webex/videos")
    public void getWebexRecordingList(){

    }


    @PostMapping("/api/webex/video")
    public void uploadVideosFromWebex() throws IOException {


        // s3로 업로드
        s3Service.uploadFileToS3(FILE_URL);
    }

}
