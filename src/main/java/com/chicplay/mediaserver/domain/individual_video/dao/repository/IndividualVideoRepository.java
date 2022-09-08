package com.chicplay.mediaserver.domain.individual_video.dao.repository;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndividualVideoRepository extends JpaRepository<IndividualVideo, UUID> {

}
