package com.chicplay.mediaserver.domain.video.dao;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoDao {

    private final VideoRepository videoRepository;

    // uuid를 통해 video return
    public Video findById(final Long id) {

        // find
        Optional<Video> video = videoRepository.findById(id);

        // not found exception
        video.orElseThrow(() -> new VideoNotFoundException(id.toString()));

        return video.get();
    }


}
