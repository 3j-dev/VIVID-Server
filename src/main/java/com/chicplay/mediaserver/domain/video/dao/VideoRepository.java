package com.chicplay.mediaserver.domain.video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.DrawingMemo;
import com.chicplay.mediaserver.domain.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, Long> {

    public String getVideoById(UUID id);

}
