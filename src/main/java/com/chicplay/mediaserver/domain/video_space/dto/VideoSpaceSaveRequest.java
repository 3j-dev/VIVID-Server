package com.chicplay.mediaserver.domain.video_space.dto;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class VideoSpaceSaveRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Builder
    public VideoSpaceSaveRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public VideoSpace toEntity() {
        return VideoSpace.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
