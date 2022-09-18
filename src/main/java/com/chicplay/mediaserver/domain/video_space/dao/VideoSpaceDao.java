package com.chicplay.mediaserver.domain.video_space.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.QIndividualVideo;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.QVideo;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import com.chicplay.mediaserver.domain.video_space.domain.QVideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.exception.VideoSpaceNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceDao {

    private final JPAQueryFactory query;

    // videoId를 통해 video return
    public VideoSpace findById(final Long id) {

        // fetch join + queryDLS를 통한 get, 2중 join
        Optional<VideoSpace> videoSpace = Optional.ofNullable(query.select(QVideoSpace.videoSpace)
                .from(QVideoSpace.videoSpace)
                .leftJoin(QVideoSpace.videoSpace.videos, QVideo.video)
                .fetchJoin()
                .where(QVideoSpace.videoSpace.id.eq(id))
                .leftJoin(QVideoSpace.videoSpace.videoSpaceParticipants, QVideoSpaceParticipant.videoSpaceParticipant)
                .fetchJoin()
                .where(QVideoSpace.videoSpace.id.eq(id))
                .distinct().fetchOne());

        // not found exception
        videoSpace.orElseThrow(() -> new VideoSpaceNotFoundException(id.toString()));

        return videoSpace.get();
    }


}
