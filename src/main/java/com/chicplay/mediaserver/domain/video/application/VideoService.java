package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.video.dao.VideoDao;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoDetailsGetResponse;
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
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoDao videoDao;

    private final VideoRepository videoRepository;

    private final VideoSpaceService videoSpaceService;

    private final IndividualVideoService individualVideoService;

    private final UserService userService;

    private final AwsS3Service awsS3Service;

    public VideoSaveResponse uploadByMultipartFile(MultipartFile multipartFile, Long videoSpaceId, VideoSaveRequest videoSaveRequest) {

        // get email
        String email = userService.getEmailFromAuthentication();

        // 해당 video의 video space find
        VideoSpace videoSpace = videoSpaceService.findById(videoSpaceId);

        // 객체 저장
        Video savedVideo = videoRepository.save(videoSaveRequest.toEntity(email));

        // aws upload by multipart file
        VideoSaveResponse videoSaveResponse = awsS3Service.uploadVideoToS3ByMultipartFile(multipartFile, savedVideo.getId());

        // space 모든 참가자들에 대해 각각의 individual videos 생성
        individualVideoService.createAfterVideoSaved(savedVideo,videoSpace);

        return videoSaveResponse;
    }

    public VideoSaveResponse uploadByDownloadUrl(String recordingDownloadUrl, Long videoSpaceId, VideoSaveRequest videoSaveRequest) throws IOException {

        // get email
        String email = userService.getEmailFromAuthentication();

        // 해당 video의 video space find
        VideoSpace videoSpace = videoSpaceService.findById(videoSpaceId);

        // 객체 저장
        Video savedVideo = videoRepository.save(videoSaveRequest.toEntity(email));

        // aws upload by download url
        VideoSaveResponse videoSaveResponse = awsS3Service.uploadVideoToS3ByDownloadUrl(recordingDownloadUrl, savedVideo.getId());

        // space 모든 참가자들에 대해 각각의 individual videos 생성
        individualVideoService.createAfterVideoSaved(savedVideo,videoSpace);

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

    public IndividualVideoDetailsGetResponse getFilePath(Long videoId) throws IOException {

        // video file path get
        String videoFilePath = awsS3Service.getVideoFilePath(videoId);

        // visual index file path list get
        List<String> visualIndexImageFilePathList = awsS3Service.getVisualIndexImages(videoId);

        IndividualVideoDetailsGetResponse individualVideoDetailsGetResponse = IndividualVideoDetailsGetResponse.builder()
                .videoFilePath(videoFilePath)
                .visualIndexImageFilePathList(visualIndexImageFilePathList)
                .build();

        return individualVideoDetailsGetResponse;
    }


}
