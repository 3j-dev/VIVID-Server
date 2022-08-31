package com.chicplay.mediaserver.domain.individual_video.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@RedisHash(value = "text_memo_state")
@DynamoDBDocument
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TextMemoState {

    // 다이노모 디비의 id 칼럼는 상속 받은 TexMemoStateHistory, Latest 클래스에 정의,
    // 때문에 DynamoDBIgnore
    @Id
    @Indexed
    @DynamoDBIgnore
    private String id;

    // 다이노모 디비의 individualVideoId 칼럼는 상속 받은 TexMemoStateHistory, Latest 클래스에 정의,
    // 때문에 DynamoDBIgnore
    @DynamoDBIgnore
    @Column(name = "individual_video_id")
    private UUID individualVideoId;

    @DynamoDBAttribute
    @Column(name = "state_json")
    protected String stateJson;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @Column(name = "video_time")
    protected LocalTime videoTime;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    public TextMemoState(String id,UUID individualVideoId, String stateJson, LocalTime videoTime, LocalDateTime createdAt) {
        this.id = id;
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdAt = createdAt;
    }
}
