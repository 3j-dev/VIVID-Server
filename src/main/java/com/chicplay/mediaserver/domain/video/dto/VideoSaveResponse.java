package com.chicplay.mediaserver.domain.video.dto;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSaveResponse {

    private Long id;

    @Builder
    public VideoSaveResponse(Long id) {
        this.id = id;
    }
}
