package com.chicplay.mediaserver.domain.individual_video.dto;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class IndividualVideoListGetResponse {


    private UUID individualVideoId;

    private LocalDateTime updatedDate;

    @Builder
    public IndividualVideoListGetResponse(IndividualVideo individualVideo) {
        this.individualVideoId = individualVideo.getId();
        this.updatedDate = individualVideo.getUpdatedDate();
    }
}
