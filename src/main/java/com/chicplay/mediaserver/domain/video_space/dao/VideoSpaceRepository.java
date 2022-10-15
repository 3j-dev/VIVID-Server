package com.chicplay.mediaserver.domain.video_space.dao;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoSpaceRepository extends JpaRepository<VideoSpace, Long> {

    List<VideoSpace> findAllByHostEmail(String hostEmail);

}
