package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public String getVisualIndexFilePath(UUID videoId){
        return videoRepository.getVideoById(videoId);
    }


}
