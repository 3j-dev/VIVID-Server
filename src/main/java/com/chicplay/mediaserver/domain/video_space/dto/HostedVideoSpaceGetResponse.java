package com.chicplay.mediaserver.domain.video_space.dto;

import com.chicplay.mediaserver.domain.user.dto.UserGetResponse;
import com.chicplay.mediaserver.domain.video.dto.VideoGetResponse;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HostedVideoSpaceGetResponse {

    private Long id;

    private String name;

    private String description;

    private List<VideoGetResponse> videos = new ArrayList<>();

    private List<UserGetResponse> users = new ArrayList<>();

    @Builder
    public HostedVideoSpaceGetResponse(VideoSpace videoSpace) {

        this.id = videoSpace.getId();
        this.name = videoSpace.getName();
        this.description = videoSpace.getDescription();
    }

    public void addVideoGetResponse(VideoGetResponse videoGetResponse) {
        this.videos.add(videoGetResponse);
    }

    public void addUserGetResponse(UserGetResponse userGetResponse) {
        this.users.add(userGetResponse);
    }

}
