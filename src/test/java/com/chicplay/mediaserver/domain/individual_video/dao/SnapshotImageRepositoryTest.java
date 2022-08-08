package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideoBuilder;
import com.chicplay.mediaserver.domain.individual_video.domain.SnapshotImage;
import com.chicplay.mediaserver.domain.individual_video.domain.SnapshotImageBuilder;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SnapshotImageRepositoryTest extends RepositoryTest {

    @Autowired
    private SnapshotImageRepository snapshotImageRepository;

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    private IndividualVideo individualVideo;
    private SnapshotImage snapshotImage;

    @BeforeEach
    public void setUp() {
        //given
        individualVideo  = individualVideoRepository.save(IndividualVideoBuilder.build());
        snapshotImage = SnapshotImageBuilder.builder(individualVideo);
    }

    @Test
    @DisplayName("[SnapshotImageRepository] save 성공 테스트")
    public void save_성공(){

        //when
        SnapshotImage savedSnapshotImage = snapshotImageRepository.save(snapshotImage);

        //then
        assertThat(savedSnapshotImage.getId()).isNotNull();
        assertThat(savedSnapshotImage.getIndividualVideo().getId()).isEqualTo(snapshotImage.getIndividualVideo().getId());
        assertThat(savedSnapshotImage.getFilePath()).isEqualTo(snapshotImage.getFilePath());
        assertThat(savedSnapshotImage.getVideoTime()).isEqualTo(snapshotImage.getVideoTime());
        assertThat(savedSnapshotImage.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedSnapshotImage.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());

    }

}