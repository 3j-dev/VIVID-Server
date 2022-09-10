package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.account.dao.AccountDao;
import com.chicplay.mediaserver.domain.account.exception.AccountNotFoundException;
import com.chicplay.mediaserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoListGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapshotImageUploadResponse;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IndividualVideoService {

    private final AccountDao accountDao;

    private final IndividualVideoRepository individualVideoRepository;

    private final AwsS3Service awsS3Service;

    public Video getVideoById(String individualVideoId) {

        // individualVideoId를 통해 video id get
        Optional<IndividualVideo> individualVideo = individualVideoRepository.findById(UUID.fromString(individualVideoId));

        // not found exception
        individualVideo.orElseThrow(() -> new VideoNotFoundException(UUID.fromString(individualVideoId)));

        return individualVideo.get().getVideo();
    }

    // user의 individualVideo 리스트를 불러온다.
//    public List<IndividualVideoListGetResponse> getListByUserId(String id) {
//
//        // user id를 통해 individualVideoList를 불러온다.
//        List<IndividualVideo> individualVideoList = accountDao.findById(UUID.fromString(id)).getIndividualVideos();
//
//        // response dto로 변환
//        ArrayList<IndividualVideoListGetResponse> individualVideoListGetRespons = new ArrayList<>();
//        individualVideoList.forEach(individualVideo -> {
//            individualVideoListGetRespons.add(IndividualVideoListGetResponse.builder().individualVideo(individualVideo).build());
//        });
//
//        return individualVideoListGetRespons;
//    }

    public SnapshotImageUploadResponse uploadSnapshotImage(MultipartFile file, String individualVideoId, String videoTime){

        // image upload, upload된 image file path get
        String snapshotImageFilePath = awsS3Service.uploadSnapshotImagesToS3(file, individualVideoId, videoTime);

        // responses dto로 변환
        SnapshotImageUploadResponse response = SnapshotImageUploadResponse.builder()
                .filePath(snapshotImageFilePath)
                .time(videoTime).build();

        return response;
    }

}
