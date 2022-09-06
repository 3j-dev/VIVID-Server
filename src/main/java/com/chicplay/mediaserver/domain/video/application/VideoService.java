package com.chicplay.mediaserver.domain.video.application;

import com.chicplay.mediaserver.domain.video.dao.VideoDao;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoDao videoDao;

    public String getVisualIndexFilePath(UUID videoId){

        Video video = videoDao.findById(videoId);

        return null;
    }


}
