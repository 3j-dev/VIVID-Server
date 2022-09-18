package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.account.application.AccountService;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceParticipantSaveRequest;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceParticipantSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantService {

    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    private final AccountService accountService;

    private final VideoSpaceService videoSpaceService;

    private final IndividualVideoService individualVideoService;

    // 이미 생성돼 있는 videoSpace에 유저 추가 : VideoSpaceParticipant save
    public VideoSpaceParticipantSaveResponse save(VideoSpaceParticipantSaveRequest videoSpaceParticipantSaveRequest) {

        // account get by email
        Account account = accountService.findByEmail();

        // video space get by videoId
        VideoSpace videoSpace = videoSpaceService.findById(videoSpaceParticipantSaveRequest.getVideoSpaceId());

        // Video participant save
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(videoSpace).account(account).build();
        VideoSpaceParticipant savedVideoSpaceParticipant = videoSpaceParticipantRepository.save(videoSpaceParticipant);

        //individual video save
        individualVideoService.createAfterAccountAddedToVideoSpace(savedVideoSpaceParticipant,videoSpace);

        VideoSpaceParticipantSaveResponse videoSpaceParticipantSaveResponse = VideoSpaceParticipantSaveResponse.builder().videoSpaceParticipant(savedVideoSpaceParticipant).build();

        return videoSpaceParticipantSaveResponse;
    }




}
