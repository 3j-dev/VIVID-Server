package com.chicplay.mediaserver.domain.video.domain;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemo;

import java.time.LocalTime;

public class SttTextBuilder {

    public static String TEXT = "이것은 매우 어려운 내용이다!";

    public static LocalTime START_TIME = LocalTime.of(00, 12, 00);

    public static Integer DURATION = 30;

    public static SttText build(Video video) {

        SttText sttText = SttText.builder().video(video).text(TEXT).duration(DURATION).startTime(START_TIME).build();

        return sttText;

    }
}
