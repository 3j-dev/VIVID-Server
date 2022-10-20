package com.chicplay.mediaserver.domain.video_space.dao;

import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoSpaceParticipantRepository extends JpaRepository<VideoSpaceParticipant, Long> {

    Optional<VideoSpaceParticipant> findByVideoSpaceAndUser(VideoSpace videoSpace, User user);
}
