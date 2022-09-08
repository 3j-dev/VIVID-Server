package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.chicplay.mediaserver.domain.individual_video.domain.*;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookmarkRepositoryTest extends RepositoryTest {

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    private IndividualVideo individualVideo;
    private Bookmark bookmark;

    @BeforeEach
    void setUp() {

        //given
        individualVideo  = individualVideoRepository.save(IndividualVideoBuilder.build());
        bookmark = BookmarkBuilder.build(individualVideo);
    }

    @Test
    @DisplayName("[BookmarkRepository] save 성공 테스트")
    public void save_성공(){

        //when
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        //then
        assertThat(savedBookmark.getId()).isNotNull();
        assertThat(savedBookmark.getIndividualVideo().getId()).isEqualTo(individualVideo.getId());
        assertThat(savedBookmark.getContent()).isEqualTo(bookmark.getContent());
        assertThat(savedBookmark.getName()).isEqualTo(bookmark.getName());
        assertThat(savedBookmark.getVideoTime()).isEqualTo(bookmark.getVideoTime());
        assertThat(savedBookmark.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedBookmark.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());

    }

}