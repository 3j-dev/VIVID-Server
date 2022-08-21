package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.*;
import com.chicplay.mediaserver.test.IntegrationTest;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TextMemoStateRedisRepositoryTest extends IntegrationTest {

    @Autowired
    private TextMemoStateRedisRepository textMemoStateRedisRepository;

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    private IndividualVideo individualVideo;

    private TextMemoState textMemoState;

    @BeforeEach
    void setUp() {

        //given
        individualVideo  = individualVideoRepository.save(IndividualVideoBuilder.build());
        textMemoState = TextMemoStateBuilder.builder(individualVideo);
    }

    @Test
    @DisplayName("[TextMemoStateRepository] save 성공 테스트")
    public void save_성공(){

        //when
        TextMemoState savedTextMemoSate = textMemoStateRedisRepository.save(textMemoState);

        //then
        assertThat(savedTextMemoSate.getId()).isNotNull();
        assertThat(savedTextMemoSate.getIndividualVideoId()).isEqualTo(individualVideo.getId());
        assertThat(savedTextMemoSate.getStateJson()).isEqualTo(textMemoState.getStateJson());
        assertThat(savedTextMemoSate.getVideoTime()).isEqualTo(textMemoState.getVideoTime());
        assertThat(savedTextMemoSate.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}