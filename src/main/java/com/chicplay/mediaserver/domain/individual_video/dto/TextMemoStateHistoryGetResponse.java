package com.chicplay.mediaserver.domain.individual_video.dto;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
@DynamoDBTable(tableName = "text_memo_state_history")
public class TextMemoStateHistoryGetResponse {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "individual_video_id")
    private String individualVideoId;

    @DynamoDBAttribute(attributeName = "state_json")
    private String stateJson;

    @DynamoDBAttribute(attributeName = "video_time")
    private String videoTime;

    @DynamoDBAttribute(attributeName = "created_at")
    private String createdAt;
}
