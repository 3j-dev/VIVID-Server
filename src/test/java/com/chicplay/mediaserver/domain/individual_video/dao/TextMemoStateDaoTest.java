package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateDynamoSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.chicplay.mediaserver.test.ContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TextMemoStateDaoTest extends ContainerBaseTest {

    @Autowired
    private TextMemoStateDao textMemoStateDao;

    @Autowired
    private TextMemoStateRepository textMemoStateRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] state를 latest state로 변환 테스트")
    public void text_memo_state_to_latest_state() {

        // given
        TextMemoStateDynamoSaveRequest requestDto = TextMemoStateDynamoSaveRequest.builder()
                .individualVideoId(String.valueOf(UUID.randomUUID()))
                .videoTime("03:10:10")
                .stateJson("qweqwe")
                .createdAt(LocalDateTime.now().toString())
                .build();

        // when
        TextMemoState textMemoStateLatest = requestDto.toLatestEntity();

        //then
        assertThat(textMemoStateLatest.getClass()).isEqualTo(TextMemoStateLatest.class);
        assertThat(textMemoStateLatest.getStateJson()).isEqualTo(requestDto.getStateJson());
        assertThat(textMemoStateLatest.getIndividualVideoId()).isEqualTo(UUID.fromString(requestDto.getIndividualVideoId()));
    }
    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoState 레디스 save 테스트")
    public void text_memo_state_redis_save_test() {

        // given
        TextMemoStateRedisSaveRequest redisSaveRequest = TextMemoStateRedisSaveRequest.builder()
                .stateJson("qwewqe")
                .individualVideoId(String.valueOf(UUID.randomUUID()))
                .videoTime("03:10:10").build();

        // when
        TextMemoState textMemoState = textMemoStateDao.saveToRedis(redisSaveRequest.toEntity());
        TextMemoStateLatest savedTextMemoState= textMemoStateDao.findTextMemoStateFromRedis(textMemoState.getIndividualVideoId().toString());

        //then
        assertThat(savedTextMemoState.getId()).isEqualTo(textMemoState.getId());
        assertThat(savedTextMemoState.getIndividualVideoId()).isEqualTo(textMemoState.getIndividualVideoId());
        assertThat(savedTextMemoState.getStateJson()).isEqualTo(textMemoState.getStateJson());

    }
}