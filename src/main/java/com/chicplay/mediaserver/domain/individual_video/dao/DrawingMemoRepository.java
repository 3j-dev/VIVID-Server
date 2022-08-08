package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.DrawingMemo;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrawingMemoRepository extends JpaRepository<DrawingMemo, UUID> {


}
