package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.DrawingMemo;
import com.chicplay.mediaserver.domain.individual_video.domain.DrawingMemoBuilder;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideoBuilder;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class DrawingMemoRepositoryTest extends RepositoryTest {

    @Autowired
    private DrawingMemoRepository drawingMemoRepository;

    @Autowired
    private IndividualVideoRepository individualVideoRepository;


    private IndividualVideo individualVideo;
    private DrawingMemo drawingMemo;

    @BeforeEach
    public void setUp() {

        //given
        individualVideo  = individualVideoRepository.save(IndividualVideoBuilder.build());
        drawingMemo = DrawingMemoBuilder.build(individualVideo);
    }

    @Test
    @DisplayName("[DrawingMemoRepository] save 성공 테스트")
    public void save_성공(){

        //when
        DrawingMemo savedDrawingMemo = drawingMemoRepository.save(drawingMemo);

        //then
        assertThat(savedDrawingMemo.getId()).isNotNull();
        assertThat(savedDrawingMemo.getIndividualVideo().getId()).isEqualTo(individualVideo.getId());
        assertThat(savedDrawingMemo.getDuration()).isEqualTo(drawingMemo.getDuration());
        assertThat(savedDrawingMemo.getFilePath()).isEqualTo(drawingMemo.getFilePath());
        assertThat(savedDrawingMemo.getStartTime()).isEqualTo(drawingMemo.getStartTime());
        assertThat(savedDrawingMemo.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedDrawingMemo.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
    }


}