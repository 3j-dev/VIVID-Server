package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.Password;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IndividualVideoRepositoryTest extends RepositoryTest {

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    private IndividualVideo individualVideo;
    private Account account;
    private Video video;

    @BeforeEach
    public void setUp() {

        //given

        String USER_EMAIL = "test@naver.com";
        String USER_NAME = "김철수";
        String USER_PASSWORD = "qwer1234";

        account = Account.builder()
                .email(USER_EMAIL).name(USER_NAME)
                .password(Password.builder().password(USER_PASSWORD).build())
                .build();

        String VIDEO_FILE_PATH = "test.aws.com";
        String VIDEO_UPLOADER_ID = "UUID-TEST-1111";
        String VIDEO_CHATTING_JSON_FILE_PATH = "test.aws.com";
        String VIDEO_INDEXING_IMAGE_FILE_PATH = "test.aws.com";

        video = Video.builder()
                .filePath(VIDEO_FILE_PATH).uploaderId(VIDEO_UPLOADER_ID)
                .chattingJsonFilePath(VIDEO_CHATTING_JSON_FILE_PATH).videoIndexingImageFilePath(VIDEO_INDEXING_IMAGE_FILE_PATH)
                .build();

        individualVideo = IndividualVideo.builder()
                .video(video).account(account).build();
    }

    @Test
    @DisplayName("[individualVideoRepository repository] save 성공 테스트")
    public void save_성공(){

        //when
        IndividualVideo savedIndividualVideo = individualVideoRepository.save(individualVideo);

        //then
        assertThat(savedIndividualVideo.getId()).isNotNull();
        assertThat(savedIndividualVideo.getVideo().getId()).isEqualTo(individualVideo.getVideo().getId());
        assertThat(savedIndividualVideo.getAccount().getId()).isEqualTo(individualVideo.getAccount().getId());

    }

}