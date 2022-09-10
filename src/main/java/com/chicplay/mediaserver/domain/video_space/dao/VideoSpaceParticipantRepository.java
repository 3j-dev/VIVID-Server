package com.chicplay.mediaserver.domain.video_space.dao;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSpaceParticipantRepository extends JpaRepository<VideoSpaceParticipant, Long> {
}
