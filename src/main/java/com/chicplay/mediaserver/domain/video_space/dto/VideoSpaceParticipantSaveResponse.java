package com.chicplay.mediaserver.domain.video_space.dto;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoSpaceParticipantSaveResponse {

    private String userEmail;

    private Long videoSpaceId;

    @Builder
    public VideoSpaceParticipantSaveResponse(VideoSpaceParticipant videoSpaceParticipant) {
        this.userEmail = videoSpaceParticipant.getUser().getEmail();
        this.videoSpaceId = videoSpaceParticipant.getVideoSpace().getId();
    }
}
