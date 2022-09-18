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

    @NotBlank
    @Email(message = "이메일을 양식을 지켜주세요.")
    private String accountEmail;

    @NotNull
    private Long videoSpaceId;

    @Builder
    public VideoSpaceParticipantSaveRequest(String accountEmail, Long videoSpaceId) {
        this.accountEmail = accountEmail;
        this.videoSpaceId = videoSpaceId;
    }
}
