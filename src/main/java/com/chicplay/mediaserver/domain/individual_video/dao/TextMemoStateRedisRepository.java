package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface TextMemoStateRedisRepository extends CrudRepository<TextMemoState, String> {

}
