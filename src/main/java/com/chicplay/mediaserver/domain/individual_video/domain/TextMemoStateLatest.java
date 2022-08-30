package com.chicplay.mediaserver.domain.individual_video.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@SuperBuilder
@Getter
@DynamoDBTable(tableName = "text_memo_state_latest")
public class TextMemoStateLatest extends TextMemoState{


    // latest state에서는 indivualVideoId가 uuid가 된다.
    @DynamoDBHashKey(attributeName = "individualVideoId")
    @Column(name = "individual_video_id")
    private UUID individualVideoId;

    public TextMemoStateLatest(String id, UUID individualVideoId, String stateJson, LocalTime videoTime, LocalDateTime createdAt) {
        super(id, individualVideoId,  stateJson, videoTime, createdAt);
        this.individualVideoId = individualVideoId;
    }
}
