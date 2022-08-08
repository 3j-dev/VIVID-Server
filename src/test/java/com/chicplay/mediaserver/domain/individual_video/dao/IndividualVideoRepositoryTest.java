package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.Password;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideoBuilder;
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

    @BeforeEach
    public void setUp() {

        //given
        individualVideo = IndividualVideoBuilder.build();
    }

    @Test
    @DisplayName("[IndividualVideoRepositoryTest] save 성공 테스트")
    public void save_성공(){

        //when
        IndividualVideo savedIndividualVideo = individualVideoRepository.save(individualVideo);

        //then
        assertThat(savedIndividualVideo.getId()).isNotNull();
        assertThat(savedIndividualVideo.getVideo().getId()).isEqualTo(individualVideo.getVideo().getId());
        assertThat(savedIndividualVideo.getAccount().getId()).isEqualTo(individualVideo.getAccount().getId());

    }

}