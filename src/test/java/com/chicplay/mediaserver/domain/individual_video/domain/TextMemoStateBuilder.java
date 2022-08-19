package com.chicplay.mediaserver.domain.individual_video.domain;

import java.time.LocalTime;

public class TextMemoStateBuilder {

    public static String STATE_JSON = "{drawings : ~~~}";

    public static LocalTime TIME = LocalTime.of(00,12,00);


    public static TextMemoState builder(IndividualVideo individualVideo) {

        TextMemoState textMemoState = TextMemoState.builder().individualVideoId(individualVideo.getId()).stateJson(STATE_JSON).
                videoTime(TIME).build();

        return textMemoState;
    }


}
