package com.chicplay.mediaserver.domain.video.dao;

import com.chicplay.mediaserver.domain.video.domain.OcrText;
import com.chicplay.mediaserver.domain.video.domain.SttText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SttTextRepository extends JpaRepository<SttText, Long> {
}
