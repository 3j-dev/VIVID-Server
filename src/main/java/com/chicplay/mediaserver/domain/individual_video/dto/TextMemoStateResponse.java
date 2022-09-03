package com.chicplay.mediaserver.domain.individual_video.dto;


import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextMemoStateResponse {

    @NotBlank
    private UUID individualVideoId;

    @NotBlank
    private String stateJson;

    @NotBlank
    private LocalTime videoTime;

    @NotBlank
    private LocalDateTime createdAt;

    @Builder
    public TextMemoStateResponse(TextMemoState textMemoState) {
        this.individualVideoId = textMemoState.getIndividualVideoId();
        this.stateJson = textMemoState.getStateJson();
        this.videoTime = textMemoState.getVideoTime();
        this.createdAt = textMemoState.getCreatedAt();
    }
}
