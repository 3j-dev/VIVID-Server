package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
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

    public VideoSpaceParticipant save(VideoSpaceParticipant videoSpaceParticipant) {
        return videoSpaceParticipantRepository.save(videoSpaceParticipant);
    }


}
