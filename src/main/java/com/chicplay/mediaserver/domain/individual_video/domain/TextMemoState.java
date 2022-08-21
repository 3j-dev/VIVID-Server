package com.chicplay.mediaserver.domain.individual_video.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@RedisHash(value = "text_memo_state")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextMemoState {

    @Id
    private String id;

    @Column(name = "individual_video_id")
    protected UUID individualVideoId;

    @Column(name = "state_json")
    private String stateJson;

    @Column(name = "video_time")
    private LocalTime videoTime;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDate;

    @Builder
    public TextMemoState(UUID individualVideoId, String stateJson, LocalTime videoTime) {
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdDate = LocalDateTime.now();
    }
}
