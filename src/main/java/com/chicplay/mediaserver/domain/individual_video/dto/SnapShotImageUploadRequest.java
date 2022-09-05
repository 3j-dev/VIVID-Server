package com.chicplay.mediaserver.domain.individual_video.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class SnapShotImageUploadRequest {

    @NotBlank
    private String videoTime;

    @Builder
    public SnapShotImageUploadRequest(String videoTime) {
        this.videoTime = videoTime;
    }
}
