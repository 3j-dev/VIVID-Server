package com.chicplay.mediaserver.domain.video.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoGetResponse {

    private Long id;

    private String individualVideoId;

    private String title;

    private String description;

    private LocalDateTime lastAccessTime;

    private Long progressRate;

    @Builder
    public VideoGetResponse(Long id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void changeIndividualVideoState(String individualVideoId, LocalDateTime lastAccessTime, Long progressRate) {
        this.individualVideoId = individualVideoId;
        this.lastAccessTime = lastAccessTime;
        this.progressRate = progressRate;
    }
}
