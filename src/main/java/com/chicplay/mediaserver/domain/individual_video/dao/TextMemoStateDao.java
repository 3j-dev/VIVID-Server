package com.chicplay.mediaserver.domain.individual_video.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TextMemoStateDao {

    private final String TEXT_MEMO_STATE_KEY = "text_memo_state";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private final DynamoDBMapper dynamoDBMapper;

    private final RedisSerializer keySerializer;

    private final RedisSerializer valueSerializer;

    public TextMemoStateDao(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper, DynamoDBMapper dynamoDBMapper) {

        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.dynamoDBMapper = dynamoDBMapper;

        this.keySerializer = this.redisTemplate.getStringSerializer();
        this.valueSerializer = this.redisTemplate.getValueSerializer();
    }


    // textMemoState를 save 합니다.
    // 이때, textMemoState를 latest 버전을 업데이트하고,
    // history 버전을 add한다.
    public TextMemoState saveToRedis(TextMemoState textMemoState) {
        redisTemplate.executePipelined((RedisCallback<Object>)connection -> {

            Map<String,Object> map = objectMapper.convertValue(textMemoState, Map.class);

            String defaultStateKey = TEXT_MEMO_STATE_KEY + "_" + textMemoState.getIndividualVideoId();

            // text_memo_state set 형식에 key값 추가.
            connection.sAdd(keySerializer.serialize(defaultStateKey), valueSerializer.serialize(textMemoState.getId()));

            // 각각의 객체들 redis에 hash 타입으로 set
            for(String key : map.keySet()) {

                // state latest version add
                connection.hashCommands().hSet(keySerializer.serialize(defaultStateKey + "_latest"),
                        valueSerializer.serialize(key),valueSerializer.serialize(map.get(key)));

                // state history version add
                connection.hashCommands().hSet(keySerializer.serialize(defaultStateKey+ "_history:" + textMemoState.getId()),
                        valueSerializer.serialize(key),valueSerializer.serialize(map.get(key)));
            }

            return null;
        });

        return textMemoState;
    }


    // textMemoState 리스트 저장
    public void saveListToRedis(List<TextMemoStateRedisSaveRequest> textMemoStates){

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

    public TextMemoState saveToDynamo(TextMemoState textMemoState) {

        dynamoDBMapper.save(textMemoState);

        return textMemoState;
    }

    public TextMemoState updateToDynamo(String id, TextMemoState textMemoState){

        dynamoDBMapper.save(textMemoState,
                new DynamoDBSaveExpression().withExpectedEntry(
                        "id", new ExpectedAttributeValue(
                                new AttributeValue().withS(textMemoState.getId())
                        )
                )
        );

        return textMemoState;
    }



}
