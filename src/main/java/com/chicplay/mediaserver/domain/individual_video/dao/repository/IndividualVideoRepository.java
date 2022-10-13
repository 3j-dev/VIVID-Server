package com.chicplay.mediaserver.domain.individual_video.dao.repository;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IndividualVideoRepository extends JpaRepository<IndividualVideo, UUID> {

    List<IndividualVideo> findAllByVideoSpaceParticipantId(Long videoSpaceParticipantId);

    List<IndividualVideo> findAllByVideoIdAndVideoSpaceParticipantId(Long videoId, Long videoSpaceParticipantId);

}
