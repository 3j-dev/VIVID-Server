package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideoBuilder;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemo;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoBuilder;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TextMemoRepositoryTest extends RepositoryTest {

    @Autowired
    private TextMemoRepository textMemoRepository;

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    private IndividualVideo individualVideo;

    private TextMemo textMemo;

    @BeforeEach
    void setUp() {
        //given
        individualVideo  = individualVideoRepository.save(IndividualVideoBuilder.build());
        textMemo = TextMemoBuilder.builder(individualVideo);

    }

    @Test
    @DisplayName("[TextMemoRepository] save 성공 테스트")
    public void save_성공(){

        //when
        TextMemo savedTextMemo = textMemoRepository.save(textMemo);

        //then
        assertThat(savedTextMemo.getId()).isNotNull();
        assertThat(savedTextMemo.getIndividualVideo().getId()).isEqualTo(individualVideo.getId());
        assertThat(savedTextMemo.getText()).isEqualTo(textMemo.getText());
        assertThat(savedTextMemo.getVideoTime()).isEqualTo(textMemo.getVideoTime());
        assertThat(savedTextMemo.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedTextMemo.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}