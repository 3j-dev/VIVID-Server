package com.chicplay.mediaserver.domain.individual_video.dto;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
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
public class TextMemoStateRedisSaveRequest {

    private String id;

    @NotBlank
    protected String individualVideoId;

    @NotBlank
    private String stateJson;

    @NotBlank
    private String videoTime;


    @Builder
    public TextMemoStateRedisSaveRequest(String individualVideoId, String stateJson, String videoTime) {
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
    }

    public TextMemoState toEntity() {

        return TextMemoState.builder()
                .id(UUID.randomUUID().toString())
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime,DateTimeFormatter.ofPattern("HH:mm:ss")))
                .createdAt(LocalDateTime.now())
                .build();

    }
}
