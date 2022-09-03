package com.chicplay.mediaserver.domain.individual_video.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateBuilder;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateHistory;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.chicplay.mediaserver.test.ContainerBaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndividualVideoApiTest extends ContainerBaseTest {

    private static final String TABLE_NAME = "text_memo_state_latest";

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDb;

    @BeforeEach
    void setUp() {

        // test container 기반, dynamoDB table 생성

        CreateTableRequest createTextMemoStateLatestTableRequest = dynamoDBMapper.generateCreateTableRequest(TextMemoStateLatest.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        CreateTableRequest createTextMemoStateHistoryTableRequest = dynamoDBMapper.generateCreateTableRequest(TextMemoStateHistory.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        TableUtils.createTableIfNotExists(amazonDynamoDb, createTextMemoStateLatestTableRequest);
        TableUtils.createTableIfNotExists(amazonDynamoDb, createTextMemoStateHistoryTableRequest);

    }

    @Test
    @DisplayName("[IndividualVideoApi] textMemoStateLatest_레디스_save")
    public void textMemoStateLatest_레디스_save() throws Exception {

        //given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoStateRedisSaveRequest textMemoStateRedisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId);

        // when
        ResultActions resultActions = mvc.perform(post("/api/individuals-videos/cache/text-memo-state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(textMemoStateRedisSaveRequest)));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[IndividualVideoApi] textMemoStateLatest_레디스에서_다이나모_save")
    public void textMemoStateLatest_레디스에서_다이나모_save() throws Exception {

        //given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        Map<String, String> request = TextMemoStateBuilder.individualVideoIdMapBuilder(individualVideoId);

        //when
        // 우선, latest state save
        mvc.perform(post("/api/individuals-videos/cache/text-memo-state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId))));

        ResultActions resultActions = mvc.perform(post("/api/individuals-videos/text-memo-state-latest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[IndividualVideoApi] textMemoStateHistoryList_레디스에서_다이나모_save")
    public void textMemoStateHistoryList_레디스에서_다이나모_save() throws Exception {

        //given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        Map<String, String> request = TextMemoStateBuilder.individualVideoIdMapBuilder(individualVideoId);

        //when
        // redis에 state 두번 save
        mvc.perform(post("/api/individuals-videos/cache/text-memo-state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId))));

        mvc.perform(post("/api/individuals-videos/cache/text-memo-state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId))));

        ResultActions resultActions = mvc.perform(post("/api/individuals-videos/text-memo-state-history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[IndividualVideoApi] textMemoStateLatest_레디스_get_return_null_and_다이노모_get()")
    public void textMemoStateLatest_레디스_get_return_null_and_다이노모_get() throws Exception {

        //given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        Map<String, String> request = TextMemoStateBuilder.individualVideoIdMapBuilder(individualVideoId);

        //when

        // redis에 저장
        mvc.perform(post("/api/individuals-videos/cache/text-memo-state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId))));

        // 다이나모에 save
        // 다이나모에 save되면 redis가 null이 된다.
        mvc.perform(post("/api/individuals-videos/text-memo-state-latest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // redis에서 get latest
        ResultActions resultActions = mvc.perform(get("/api/individuals-videos/cache/text-memo-state-latest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions
                .andExpect(jsonPath("individualVideoId").value(individualVideoId));


    }


}