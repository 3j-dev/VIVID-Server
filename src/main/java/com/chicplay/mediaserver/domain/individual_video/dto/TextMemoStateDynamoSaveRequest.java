package com.chicplay.mediaserver.domain.individual_video.dto;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateHistory;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class TextMemoStateDynamoSaveRequest {

    @NotBlank
    private String id;

    @NotBlank
    protected String individualVideoId;

    @NotBlank
    private String stateJson;

    @NotBlank
    private String videoTime;

    @NotBlank
    private String createdAt;


    @Builder
    public TextMemoStateDynamoSaveRequest(String individualVideoId, String stateJson, String videoTime, String createdAt) {
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdAt = createdAt;
    }

    public TextMemoStateLatest toLatestEntity() {

        return TextMemoStateLatest.builder()
                .id(id)
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime, DateTimeFormatter.ofPattern("HH:mm:ss")))
                .createdAt(LocalDateTime.parse(createdAt))
                .build();
    }

    public TextMemoStateHistory toHistoryEntity() {

        return TextMemoStateHistory.builder()
                .id(id)
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime,DateTimeFormatter.ofPattern("HH:mm:ss")))
                .createdAt(LocalDateTime.parse(createdAt))
                .build();
    }
}
