package com.chicplay.mediaserver.domain.individual_video.domain;

import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateDynamoSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TextMemoStateBuilder {

    public static String INDIVIDUAL_VIDEO_ID = "2f7a0321-d011-4aa0-a549-bf4f45f4bc1a";

    public static String STATE_JSON = "{drawings : ~~~}";

    public static String VIDEO_TIME = "01:01:01";


    public static TextMemoStateRedisSaveRequest redisSaveRequestBuilder() {

        TextMemoStateRedisSaveRequest textMemoStateRedisSaveRequest = TextMemoStateRedisSaveRequest.builder()
                .individualVideoId(INDIVIDUAL_VIDEO_ID)
                .stateJson(STATE_JSON)
                .videoTime(VIDEO_TIME)
                .build();

        return textMemoStateRedisSaveRequest;
    }

    public static TextMemoStateDynamoSaveRequest dynamoSaveRequestBuilder() {

        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest = TextMemoStateDynamoSaveRequest.builder()
                .id(UUID.randomUUID().toString())
                .individualVideoId(INDIVIDUAL_VIDEO_ID)
                .videoTime(VIDEO_TIME)
                .stateJson(STATE_JSON )
                .createdAt(LocalDateTime.now().toString())
                .build();

        return textMemoStateDynamoSaveRequest;
    }

    public static Map<String,String> individualVideoIdMapBuilder(){
        Map<String, String> request = new HashMap<>();
        request.put("individualVideoId", TextMemoStateBuilder.INDIVIDUAL_VIDEO_ID);
        return request;
    }


}
