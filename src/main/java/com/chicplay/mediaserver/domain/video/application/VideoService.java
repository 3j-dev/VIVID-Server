package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.video.dao.VideoDao;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.dto.VideoFilePathGetResponse;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoDao videoDao;

    private final AwsS3Service awsS3Service;

    public String getVisualIndexFilePath(UUID videoId){

        //Video video = videoDao.findById(videoId);

        return null;
    }

    public VideoFilePathGetResponse getFilePath(String videoId) throws IOException {

        // video file path get
        String videoFilePath = awsS3Service.getVideoFilePath(videoId);

        // visual index file path list get
        List<String> visualIndexImageFilePathList = awsS3Service.getVisualIndexImages(videoId);

        VideoFilePathGetResponse videoFilePathGetResponse = VideoFilePathGetResponse.builder()
                .videoFilePath(videoFilePath)
                .visualIndexImageFilePathList(visualIndexImageFilePathList)
                .build();

        return videoFilePathGetResponse;
    }


}
