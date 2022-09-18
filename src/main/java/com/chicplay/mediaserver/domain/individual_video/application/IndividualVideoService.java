package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.account.dao.AccountDao;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.exception.AccountNotFoundException;
import com.chicplay.mediaserver.domain.individual_video.dao.IndividualVideoDao;
import com.chicplay.mediaserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoListGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapshotImageUploadResponse;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
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

    private final IndividualVideoDao individualVideoDao;

    private final AwsS3Service awsS3Service;

    public Video getVideoById(String individualVideoId) {

        // individualVideoId를 통해 video id get
        Optional<IndividualVideo> individualVideo = individualVideoRepository.findById(UUID.fromString(individualVideoId));

        // not found exception
        individualVideo.orElseThrow(() -> new VideoNotFoundException(individualVideoId));

        return individualVideo.get().getVideo();
    }

    // video save 된 후, video space의 account 전부에게 individual video 생성
    public void createAfterVideoSaved(Video video, VideoSpace videoSpace) {

        List<IndividualVideo> individualVideos = new ArrayList<>();

        // 각각의 space 참가자의 individual video를 생성하기 위함
        videoSpace.getVideoSpaceParticipants().forEach( videoSpaceParticipant -> {

            // list에 individulaVideo 객체를 각각 생성해서 add
            individualVideos.add(IndividualVideo.builder().video(video).videoSpaceParticipant(videoSpaceParticipant).build());
        });

        // jpa bulk insert, uuid 방식이어서 jpa bulk insert 가능
        individualVideoRepository.saveAll(individualVideos);

    }

    // video space에 신규 유저가 add 된후, 모든 영상에서 대해 individual video 생성
    public void createAfterAccountAddedToVideoSpace(VideoSpaceParticipant videoSpaceParticipant, VideoSpace videoSpace) {

        List<IndividualVideo> individualVideos = new ArrayList<>();

        videoSpace.getVideos().forEach( video -> {

            // list에 individulaVideo 객체를 각각 생성해서 add
            individualVideos.add(IndividualVideo.builder().video(video).videoSpaceParticipant(videoSpaceParticipant).build());
        });

        // jpa bulk insert, uuid 방식이어서 jpa bulk insert 가능
        individualVideoRepository.saveAll(individualVideos);

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

    public SnapshotImageUploadResponse uploadSnapshotImage(MultipartFile file, String individualVideoId, String videoTime) {

        // image upload, upload된 image file path get
        String snapshotImageFilePath = awsS3Service.uploadSnapshotImagesToS3(file, individualVideoId, videoTime);

        // responses dto로 변환
        SnapshotImageUploadResponse response = SnapshotImageUploadResponse.builder()
                .filePath(snapshotImageFilePath)
                .time(videoTime).build();

        return response;
    }

}
