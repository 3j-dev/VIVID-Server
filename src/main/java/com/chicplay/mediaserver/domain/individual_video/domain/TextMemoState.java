package com.chicplay.mediaserver.domain.individual_video.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@RedisHash(value = "text_memo_state")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextMemoState {

    @Id
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute
    @Column(name = "individual_video_id")
    protected UUID individualVideoId;

    @DynamoDBAttribute
    @Column(name = "state_json")
    private String stateJson;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @Column(name = "video_time")
    private LocalTime videoTime;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public TextMemoState(String id, UUID individualVideoId, String stateJson, LocalTime videoTime, LocalDateTime createdAt) {
        this.id = id;
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdAt = createdAt;
    }
}
