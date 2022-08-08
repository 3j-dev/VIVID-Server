package com.chicplay.mediaserver.domain.video.dao;

import com.chicplay.mediaserver.domain.video.domain.OcrText;
import com.chicplay.mediaserver.domain.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OcrTextRepository extends JpaRepository<OcrText, UUID> {


}
