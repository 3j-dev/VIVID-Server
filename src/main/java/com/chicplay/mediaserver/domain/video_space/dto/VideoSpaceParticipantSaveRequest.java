package com.chicplay.mediaserver.domain.video_space.dto;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class VideoSpaceParticipantSaveRequest {

    @NotNull
    private Long videoSpaceId;

    @Builder
    public VideoSpaceParticipantSaveRequest(Long videoSpaceId) {
        this.videoSpaceId = videoSpaceId;
    }
}
