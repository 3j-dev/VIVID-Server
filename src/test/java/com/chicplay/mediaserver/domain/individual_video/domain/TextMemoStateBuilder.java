package com.chicplay.mediaserver.domain.individual_video.domain;

import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;

import java.time.LocalTime;

public class TextMemoStateBuilder {

    public static String INDIVIDUAL_VIDEO_ID = "2f7a0321-d011-4aa0-a549-bf4f45f4bc1a";

    public static String STATE_JSON = "{drawings : ~~~}";

    public static String VIDEO_TIME = "01:01:01";


    public static TextMemoStateRedisSaveRequest saveRequestBuilder() {

        TextMemoStateRedisSaveRequest textMemoStateRedisSaveRequest = TextMemoStateRedisSaveRequest.builder()
                .individualVideoId(INDIVIDUAL_VIDEO_ID)
                .stateJson(STATE_JSON)
                .videoTime(VIDEO_TIME)
                .build();

        return textMemoStateRedisSaveRequest;
    }


}
