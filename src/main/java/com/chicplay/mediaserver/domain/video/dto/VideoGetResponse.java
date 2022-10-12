package com.chicplay.mediaserver.domain.video.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoGetResponse {

    private Long id;

    private String individualVideoId;

    private String title;

    private String description;

    @Builder
    public VideoGetResponse(Long id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void changeIndividualVideoId(String individualVideoId) {
        this.individualVideoId = individualVideoId;
    }
}
