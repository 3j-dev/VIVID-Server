package com.chicplay.mediaserver.domain.individual_video.domain;

import java.time.LocalTime;

public class SnapshotImageBuilder {

    public static String FILE_PATH = "test.aws.com";

    public static LocalTime VIDEO_TIME = LocalTime.of(00,12,00);


    public static SnapshotImage builder(IndividualVideo individualVideo) {

        SnapshotImage snapshotImage = SnapshotImage.builder().individualVideo(individualVideo).filePath(FILE_PATH)
                .videoTime(VIDEO_TIME).build();

        return snapshotImage;
    }


}
