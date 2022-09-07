package com.chicplay.mediaserver.domain.individual_video.dao;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TextMemoStateRepository extends CrudRepository<TextMemoState, String> {

    Optional<TextMemoState> findById(String id);

    Optional<TextMemoState> findTextMemoStateById(String id);

}
