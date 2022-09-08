package com.chicplay.mediaserver.domain.video.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class VideoFilePathGetResponse {


    private String videoFilePath;

    private List<String> visualIndexImageFilePathList;

    @Builder
    public VideoFilePathGetResponse(String videoFilePath, List<String> visualIndexImageFilePathList) {
        this.videoFilePath = videoFilePath;
        this.visualIndexImageFilePathList = visualIndexImageFilePathList;
    }
}
