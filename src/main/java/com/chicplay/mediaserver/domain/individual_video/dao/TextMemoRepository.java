package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TextMemoRepository extends JpaRepository<TextMemo, UUID> {


}
