package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import org.springframework.data.repository.CrudRepository;

public interface TextMemoStateRedisRepository extends CrudRepository<TextMemoState, String> {

}
