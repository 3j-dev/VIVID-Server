package com.chicplay.mediaserver.domain.video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.QIndividualVideo;
import com.chicplay.mediaserver.domain.video.domain.QVideo;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    private final JPAQueryFactory query;

    // videoId를 통해 video return
    public Video findById(final Long id) {

        // fetch join + queryDLS를 통한 get
        Optional<Video> video = Optional.ofNullable(query.select(QVideo.video)
                .from(QVideo.video)
                .leftJoin(QVideo.video.individualVideos, QIndividualVideo.individualVideo)
                .fetchJoin()
                .where(QVideo.video.id.eq(id))
                .distinct().fetchOne());

        // not found exception
        video.orElseThrow(() -> new VideoNotFoundException(id.toString()));

        return video.get();
    }


}
