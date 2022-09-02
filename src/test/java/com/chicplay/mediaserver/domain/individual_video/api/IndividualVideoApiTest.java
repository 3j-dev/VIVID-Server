package com.chicplay.mediaserver.domain.individual_video.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateBuilder;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.chicplay.mediaserver.test.ContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndividualVideoApiTest extends ContainerBaseTest {

    private static final String TABLE_NAME = "text_memo_state_latest";

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDb;

    @BeforeEach
    void setUp() {

        // dynamoDB table 생성
        // test container 기반,
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(TextMemoStateLatest.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
    }

    @Test
    @DisplayName("[IndividualVideoApi] textMemoStateLatest_레디스_save")
    public void textMemoStateLatest_레디스_저장() throws Exception {

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
    public void textMemoStateLatest_레디스에서_다이나모_저장() throws Exception {

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
    @DisplayName("[IndividualVideoApi] textMemoStateHistory_레디스에서_다이나모_save")
    public void textMemoStateHistory_레디스에서_다이나모_저장() throws Exception {

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



}