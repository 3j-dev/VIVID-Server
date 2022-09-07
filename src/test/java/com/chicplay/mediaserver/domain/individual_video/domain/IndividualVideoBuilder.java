package com.chicplay.mediaserver.domain.individual_video.domain;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.AccountBuilder;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.domain.VideoBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class IndividualVideoBuilder {

    public static IndividualVideo build() {

        Video video = VideoBuilder.build();
        Account account = AccountBuilder.build();

        IndividualVideo individualVideo = IndividualVideo.builder()
                .video(video).account(account).build();

        return individualVideo;
    }

    public static IndividualVideo build(Account account,Video video) {

        IndividualVideo individualVideo = IndividualVideo.builder()
                .video(video).account(account).build();

        return individualVideo;
    }

}