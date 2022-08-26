package com.chicplay.mediaserver.domain.individual_video.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
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
@DynamoDBTable(tableName = "text_memo_state")
public class TextMemoState {

    @Id
    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    @Column(name = "individual_video_id")
    protected UUID individualVideoId;

    @DynamoDBAttribute
    @Column(name = "state_json")
    private String stateJson;

    @DynamoDBAttribute
    @Column(name = "video_time")
    private LocalTime videoTime;

    @DynamoDBAttribute
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDate;

    @Builder
    public TextMemoState(String id, UUID individualVideoId, String stateJson, LocalTime videoTime) {
        this.id = id;
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdDate = LocalDateTime.now();
    }
}
