package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.exception.VideoSpaceNotFoundException;
import com.chicplay.mediaserver.domain.video_space.exception.VideoSpaceParticipantNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantFindService {
    
    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    public VideoSpaceParticipant findById(Long videoSpaceParticipantId) {

        // find video space by id
        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantRepository.findById(videoSpaceParticipantId).orElseThrow(VideoSpaceParticipantNotFoundException::new);

        return videoSpaceParticipant;
    }

    public VideoSpaceParticipant findByUserAndVideoSpace(User user, VideoSpace videoSpace) {

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantRepository.findByVideoSpaceAndUser(videoSpace, user)
                .orElseThrow(VideoSpaceParticipantNotFoundException::new);

        return videoSpaceParticipant;
    }
    
}
