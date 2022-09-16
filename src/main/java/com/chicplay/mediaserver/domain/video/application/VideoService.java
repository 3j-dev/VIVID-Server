package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.video.dao.VideoDao;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.dto.VideoFilePathGetResponse;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveRequest;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveResponse;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoDao videoDao;

    private final VideoRepository videoRepository;

    private final VideoSpaceService videoSpaceService;

    private final AwsS3Service awsS3Service;

    public VideoSaveResponse upload(MultipartFile multipartFile, VideoSaveRequest videoSaveRequest) {

        // 해당 video의 find
        VideoSpace videoSpace = videoSpaceService.findById(videoSaveRequest.getVideoSpaceId());

        // 객체 저장
        Video savedVideo = videoRepository.save(videoSaveRequest.toEntity(videoSpace));

        // aws upload
        VideoSaveResponse videoSaveResponse = awsS3Service.uploadVideoToS3(multipartFile, savedVideo.getId());

        return videoSaveResponse;
    }

    // upload 된 후 video의 uploaded 상태 변경
    public void changeUploadState(Long videoId, boolean isUploaded) {

        // 해당 video의 find
        Video video = videoDao.findById(videoId);

        // uploaded true
        video.changeIsUploaded(isUploaded);
    }

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
