package com.chicplay.mediaserver.domain.individual_video.domain;

import java.time.LocalTime;

public class TextMemoBuilder {

    public static String TEXT= "정말 어려운 내용이다!";

    public static LocalTime VIDEO_TIME = LocalTime.of(00,12,00);


    public static TextMemo builder(IndividualVideo individualVideo) {

        TextMemo textMemo = TextMemo.builder().individualVideo(individualVideo).text(TEXT)
                .videoTime(VIDEO_TIME).build();

        return textMemo;
    }


}
