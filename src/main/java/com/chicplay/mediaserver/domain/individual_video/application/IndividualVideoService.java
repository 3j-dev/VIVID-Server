package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateRepository;
import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateDao;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateDynamoSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndividualVideoService {

    private final TextMemoStateRepository textMemoStateRepository;

    private final TextMemoStateDao textMemoStateDao;


    // redis에 textMemoState 객체 하나 저장 메소드
    @Transactional
    public void saveTextMemoStateToRedis(final TextMemoStateRedisSaveRequest textMemoState){

        textMemoStateDao.saveToRedis(textMemoState.toEntity());
    }

    @Transactional
    public TextMemoStateLatest getTextMemoStateFromRedis(String id){

        return textMemoStateDao.findTextMemoStateFromRedis(id);
    }

    // redis에 textMemoState 객체 리스트 저장 메소드
    public void saveTextMemoStatesToRedis(List<TextMemoStateRedisSaveRequest> textMemoStates){

        textMemoStateDao.saveListToRedis(textMemoStates);
    }

    // dynamo db에 textMemoStateLatest문 insert
    public void saveTextMemoStateLatestToDynamoDb(TextMemoStateDynamoSaveRequest textMemoState){

        textMemoStateDao.saveToDynamo(textMemoState.toLatestEntity());
    }

    // dynamo db에 textMemoStateHistroy문 insert
    public void saveTextMemoStateHistoryToDynamoDb(List<TextMemoStateDynamoSaveRequest> textMemoStates){

        textMemoStateDao.saveListToDynamo(textMemoStates);
    }




}
