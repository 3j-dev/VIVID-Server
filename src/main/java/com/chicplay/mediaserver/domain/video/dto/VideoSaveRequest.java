package com.chicplay.mediaserver.domain.video.dto;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Schema
public class VideoSaveRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long videoSpaceId;

    public Video toEntity(VideoSpace videoSpace) {
        return Video.builder()
                .title(this.title)
                .description(this.description)
                .videoSpace(videoSpace)
                .uploaderId("test")
                .build();
    }

}
