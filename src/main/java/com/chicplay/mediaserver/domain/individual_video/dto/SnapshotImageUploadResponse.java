package com.chicplay.mediaserver.domain.individual_video.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotImageUploadResponse {

    @Schema(description = "이미지 파일 경로" , example = "test01.aws.com")
    private String filePath;

    @Schema(description = "스냅샷 캡처 시간(초)" , example = "120")
    private Long time;

}
