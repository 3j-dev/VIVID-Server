package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceParticipantSaveResponse;
import com.chicplay.mediaserver.domain.video_space.exception.VideoSpaceParticipantDuplicatedException;
import com.chicplay.mediaserver.domain.video_space.exception.VideoSpaceParticipantNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantService {

    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    private final UserService userService;

    private final VideoSpaceService videoSpaceService;

    // 이미 생성돼 있는 videoSpace에 유저 추가 : VideoSpaceParticipant save
    public VideoSpaceParticipantSaveResponse save(Long videoSpaceId, String userEmail) {

        // account get by email
        User user = userService.findByEmail(userEmail);

        // video space get by videoId
        VideoSpace videoSpace = videoSpaceService.findById(videoSpaceId);

        // exception by duplicated user
        videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
            if(videoSpaceParticipant.getUser().getEmail().equals(userEmail))
                throw new VideoSpaceParticipantDuplicatedException();
        });

        // Video participant save
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(videoSpace).user(user).build();
        VideoSpaceParticipant savedVideoSpaceParticipant = videoSpaceParticipantRepository.save(videoSpaceParticipant);

        // list에 individulaVideo 객체를 각각 생성해서 add
        videoSpace.getVideos().forEach(video -> {
            savedVideoSpaceParticipant.getIndividualVideos()
                    .add(IndividualVideo.builder().video(video).videoSpaceParticipant(savedVideoSpaceParticipant).build());
        });

        VideoSpaceParticipantSaveResponse videoSpaceParticipantSaveResponse = VideoSpaceParticipantSaveResponse.builder().videoSpaceParticipant(savedVideoSpaceParticipant).build();

        return videoSpaceParticipantSaveResponse;
    }

    public VideoSpaceParticipant findById(Long videoSpaceParticipantId) {
        Optional<VideoSpaceParticipant> videoSpaceParticipant = videoSpaceParticipantRepository.findById(videoSpaceParticipantId);

        // not found exception
        videoSpaceParticipant.orElseThrow(() -> new VideoSpaceParticipantNotFoundException(Long.toString(videoSpaceParticipantId)));

        return videoSpaceParticipant.get();
    }

    private void checkValidUserAccessFromId(Long videoSpaceParticipantId) {

        VideoSpaceParticipant videoSpaceParticipant = findById(videoSpaceParticipantId);

        // user 권한 체크
        userService.checkValidUserAccess(videoSpaceParticipant.getUser().getEmail());
    }




}
