package com.chicplay.mediaserver.domain.video_space.dto;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class VideoSpaceParticipantSaveResponse {

    private String accountEmail;

    private Long videoSpaceId;

    @Builder
    public VideoSpaceParticipantSaveResponse(VideoSpaceParticipant videoSpaceParticipant) {
        this.accountEmail = videoSpaceParticipant.getAccount().getEmail();
        this.videoSpaceId = videoSpaceParticipant.getVideoSpace().getId();
    }
}
