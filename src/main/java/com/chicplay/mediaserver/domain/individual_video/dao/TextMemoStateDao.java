package com.chicplay.mediaserver.domain.individual_video.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateHistory;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class TextMemoStateDao {

    private final TextMemoStateRepository textMemoStateRepository;

    private final String TEXT_MEMO_STATE_KEY = "text_memo_state";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private final DynamoDBMapper dynamoDBMapper;

    private final RedisSerializer keySerializer;

    private final RedisSerializer valueSerializer;


    public TextMemoStateDao(TextMemoStateRepository textMemoStateRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper,
                            DynamoDBMapper dynamoDBMapper) {

        this.textMemoStateRepository = textMemoStateRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.dynamoDBMapper = dynamoDBMapper;

        this.keySerializer = this.redisTemplate.getStringSerializer();
        this.valueSerializer = this.redisTemplate.getValueSerializer();
    }

    // redis의 state Key를 조합해주는 메소드.
    // latest 버전은 + _latest
    // history 버전은 + _history
    public String getDefaultStateKey(String individualVideoId) {
        return TEXT_MEMO_STATE_KEY + "_" + individualVideoId;
    }

    // textMemoState를 save 합니다.
    // 이때, textMemoState를 latest 버전을 업데이트하고,
    // history 버전을 add한다.
    public TextMemoState saveToRedis(TextMemoState textMemoState) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

            Map<String, Object> map = objectMapper.convertValue(textMemoState, Map.class);

            String defaultStateKey = getDefaultStateKey(textMemoState.getIndividualVideoId().toString());

            // text_memo_state set 형식에 key값 추가.
            connection.sAdd(keySerializer.serialize(defaultStateKey), valueSerializer.serialize(textMemoState.getId()));

            // 각각의 객체들 redis에 hash 타입으로 set
            for (String key : map.keySet()) {

                // state latest version add
                connection.hashCommands().hSet(keySerializer.serialize(defaultStateKey + "_latest"),
                        valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));

                // state history version add
                connection.hashCommands().hSet(keySerializer.serialize(defaultStateKey + "_history:" + textMemoState.getId()),
                        valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));
            }

            return null;
        });

        return textMemoState;
    }


    // textMemoState 리스트 저장
    public void saveListToRedis(List<TextMemoStateRedisSaveRequest> textMemoStates) {

        // redis pipeline
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            textMemoStates.forEach(textMemoState -> {

                TextMemoState textMemoStateEntity = textMemoState.toEntity();

                Map<String, Object> map = objectMapper.convertValue(textMemoStateEntity, Map.class);

                // text_memo_state set 형식에 key값 추가.
                connection.sAdd(keySerializer.serialize(TEXT_MEMO_STATE_KEY),
                        valueSerializer.serialize(textMemoStateEntity.getId()));

                // 각각의 객체들 redis에 hash 타입으로 set
                for (String key : map.keySet()) {
                    connection.hashCommands().hSet(keySerializer.serialize(TEXT_MEMO_STATE_KEY + ":" + textMemoStateEntity.getId()),
                            valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));
                }
            });
            return null;
        });
    }

    // videoId를 통해서 state latest get
    public TextMemoStateLatest findTextMemoStateLatestFromRedis(String individualVideoId) {

        TextMemoStateLatest textMemoStateLatest = objectMapper.convertValue(redisTemplate.opsForHash()
                .entries(getDefaultStateKey(individualVideoId) + "_latest"), TextMemoStateLatest.class);

        return textMemoStateLatest;
    }

    // key_history를 통해 레디스에서 find and 객체 list return
    public List<TextMemoStateHistory> getTextMemoStateHistoryFromRedis(String individualVideoId) {

        List<TextMemoStateHistory> list = new ArrayList<>();

        // redis pipeline
        redisTemplate.execute((RedisCallback<List<String>>) connection -> {


            // macth된 key 값 검색
            ScanOptions options = ScanOptions.scanOptions().match(getDefaultStateKey(individualVideoId) + "_history*")
                    .count(100L).build();
            Cursor cursor = connection.scan(options);

            while (cursor.hasNext()) {

                TextMemoStateHistory textMemoStateHistory = objectMapper.convertValue(redisTemplate.opsForHash()
                        .entries(new String((byte[]) cursor.next())), TextMemoStateHistory.class);

                list.add(textMemoStateHistory);

            }
            return null;
        });

        return list;
    }

    // 다이노모db에 latest save
    public void saveLatestToDynamo(TextMemoStateLatest textMemoStateLatest) {

        dynamoDBMapper.save(textMemoStateLatest);
    }

    // 다이노모db에 history save
    public void saveHistoryListToDynamo(List<TextMemoStateHistory> textMemoStateHistoryList) {

        dynamoDBMapper.batchSave(textMemoStateHistoryList);
    }

    // 다이노모db에서 latest get
    public TextMemoStateLatest getLatestFromDynamo(String individualVideoId) {

        TextMemoStateLatest textMemoStateLatest = dynamoDBMapper.load(TextMemoStateLatest.class, UUID.fromString(individualVideoId));

        return textMemoStateLatest;
    }

    // 다이노모db에서 history get
    public List<TextMemoStateHistory> getHistoryListFromDynamo(String individualVideoId) {

        Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
        map.put(":val", new AttributeValue().withS(individualVideoId));

        DynamoDBQueryExpression<TextMemoStateHistory> queryExpression = new DynamoDBQueryExpression<TextMemoStateHistory>()
                .withKeyConditionExpression("individual_video_id = :val")
                .withExpressionAttributeValues(map);

        List<TextMemoStateHistory> scanResult = dynamoDBMapper.query(TextMemoStateHistory.class, queryExpression);

        return scanResult;
    }

    public TextMemoState updateToDynamo(String id, TextMemoState textMemoState) {

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
