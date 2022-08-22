package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateRedisRepository;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateSaveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndividualVideoService {

    private final TextMemoStateRedisRepository textMemoStateRedisRepository;

    private final String TEXT_MEMO_STATE_KEY = "text_memo_state";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;


    @Transactional
    public TextMemoState saveTextMemoState (final TextMemoStateSaveRequest dto){

        return textMemoStateRedisRepository.save(dto.toEntity());
    }

    public void saveTextMemoStates (List<TextMemoStateSaveRequest> textMemoStates){

        RedisSerializer keySerializer = redisTemplate.getStringSerializer();
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();

        // redis pipeline
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            textMemoStates.forEach(textMemoState -> {

                TextMemoState textMemoStateEntity = textMemoState.toEntity();

                Map<String,Object> map = objectMapper.convertValue(textMemoStateEntity, Map.class);

                // text_memo_state set 형식에 key값 추가.
                connection.sAdd(keySerializer.serialize(TEXT_MEMO_STATE_KEY),
                        valueSerializer.serialize(textMemoStateEntity.getId()));

                // 각각의 객체들 redis에 hash 타입으로 set
                for(String key : map.keySet()) {
                    connection.hashCommands().hSet(keySerializer.serialize(TEXT_MEMO_STATE_KEY + ":" + textMemoStateEntity.getId()),
                            valueSerializer.serialize(key),valueSerializer.serialize(map.get(key)));
                }
            });
            return null;
        });
    }




}
