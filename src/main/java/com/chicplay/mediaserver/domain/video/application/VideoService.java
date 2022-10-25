package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.exception.UserAccessDeniedException;
import com.chicplay.mediaserver.domain.video.dao.VideoDao;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoDetailsGetResponse;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveRequest;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveResponse;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceFindService;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
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

    private final VideoSpaceFindService videoSpaceFindService;

    private final IndividualVideoService individualVideoService;

    private final UserService userService;

    private final AwsS3Service awsS3Service;

    // multipart 파일을 통한 직접 업로드 메소드
    public VideoSaveResponse uploadByMultipartFile(MultipartFile multipartFile, Long videoSpaceId, VideoSaveRequest videoSaveRequest) {

        // get email
        String email = userService.getEmailFromAuthentication();

        // 해당 video의 video space find
        VideoSpace videoSpace = videoSpaceFindService.findById(videoSpaceId);

        // video space의 host email과 현재 접속 user의 email이 불일치할 경우, throw
        if(!videoSpace.getHostEmail().equals(email))
            throw new UserAccessDeniedException();

        // 객체 저장
        Video savedVideo = videoRepository.save(videoSaveRequest.toEntity(videoSpace, email));

        // aws upload by multipart file
        VideoSaveResponse videoSaveResponse = awsS3Service.uploadVideoToS3ByMultipartFile(multipartFile, savedVideo.getId());

        // space 모든 참가자들에 대해 각각의 individual videos 생성
        individualVideoService.createAfterVideoSaved(savedVideo,videoSpace);

        return videoSaveResponse;
    }

    // download url을 통한 video 업로드
    public VideoSaveResponse uploadByDownloadUrl(String recordingDownloadUrl, Long videoSpaceId, VideoSaveRequest videoSaveRequest) throws IOException {

        // get email
        String email = userService.getEmailFromAuthentication();

        // 해당 video의 video space find
        VideoSpace videoSpace = videoSpaceFindService.findById(videoSpaceId);

        // video space의 host email과 현재 접속 user의 email이 불일치할 경우, throw
        if(!videoSpace.getHostEmail().equals(email))
            throw new UserAccessDeniedException();

        // 객체 저장
        Video savedVideo = videoRepository.save(videoSaveRequest.toEntity(videoSpace, email));

        // aws upload by download url
        VideoSaveResponse videoSaveResponse = awsS3Service.uploadVideoToS3ByDownloadUrl(recordingDownloadUrl, savedVideo.getId());

        // space 모든 참가자들에 대해 각각의 individual videos 생성
        individualVideoService.createAfterVideoSaved(savedVideo,videoSpace);

        return videoSaveResponse;
    }

    // delete
    public void delete(Long videoId) {

        // 해당 video의 find
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);

        // 삭제 연관 관계 편의 메소드
        video.delete();

        // delete by id
        videoRepository.deleteById(videoId);
    }

    // upload 된 후 video의 uploaded 상태 변경
    public void changeUploadState(Long videoId, boolean isUploaded) throws IOException {

        // 해당 video의 find
//        Video video = videoDao.findById(videoId);
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);

        // uploaded true
        video.changeIsUploaded(isUploaded);

        // get thumbnail imgae
        List<String> visualIndexImages = awsS3Service.getVisualIndexImages(videoId);

        // video thumbnail change
        video.changeThumbnailImagePath(visualIndexImages.get(0));

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
