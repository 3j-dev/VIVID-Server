package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.Bookmark;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookmarkRepository  extends JpaRepository<Bookmark, UUID> {

}
