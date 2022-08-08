package com.chicplay.mediaserver.domain.video.dao;

import com.chicplay.mediaserver.domain.video.domain.OcrText;
import com.chicplay.mediaserver.domain.video.domain.OcrTextBuilder;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.domain.VideoBuilder;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OcrTextRepositoryTest extends RepositoryTest {

    @Autowired
    private OcrTextRepository ocrTextRepository;

    @Autowired
    private VideoRepository videoRepository;

    private Video video;

    private OcrText ocrText;


    @BeforeEach
    void setUp() {

        //given
        video = videoRepository.save(VideoBuilder.build());
        ocrText = OcrTextBuilder.build(video);
    }

    @Test
    @DisplayName("[OcrTextRepository] save 성공 테스트")
    public void save_성공(){

        //when
        OcrText savedOcrText = ocrTextRepository.save(ocrText);

        //then
        assertThat(savedOcrText.getId()).isNotNull();
        assertThat(savedOcrText.getVideo().getId()).isEqualTo(video.getId());
        assertThat(savedOcrText.getText()).isEqualTo(ocrText.getText());
        assertThat(savedOcrText.getStartTime()).isEqualTo(ocrText.getStartTime());
        assertThat(savedOcrText.getDuration()).isEqualTo(ocrText.getDuration());
        assertThat(savedOcrText.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedOcrText.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());

    }
}