package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateRepository;
import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateDao;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateHistory;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
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

    // redis로 부터 latest get
    @Transactional
    public TextMemoStateLatest getTextMemoStateLatestFromRedis(String individualVideoId){

        // redis return 값이 null 일 경우, dynamoDB에서 get
        TextMemoStateLatest textMemoStateLatest = Optional
                .ofNullable(textMemoStateDao.getTextMemoStateLatestFromRedis(individualVideoId))
                .orElse(textMemoStateDao.getLatestFromDynamo(individualVideoId));   // null일 경우 dyanamo에서 get

        return textMemoStateLatest;
    }

    // dynamoDB에서 history list get
    @Transactional
    public List<TextMemoStateHistory> getTextMemoStateHistoryFromDynamoDb(String individualVideoId){

        List<TextMemoStateHistory> historyListFromDynamo = textMemoStateDao.getHistoryListFromDynamo(individualVideoId);

        return historyListFromDynamo;
    }

    // dynamo db에 textMemoStateLatest문 insert
    // save시 redis에서 delete
    @Transactional
    public void saveTextMemoStateLatestToDynamoDb(String individualVideoId){

        // 요청된 individualVideoId의 state latest를 redis에서 가져온다.
        TextMemoStateLatest textMemoStateLatest = textMemoStateDao.getTextMemoStateLatestFromRedis(individualVideoId);

        // 해당 state를 저장한다.
        textMemoStateDao.saveLatestToDynamo(textMemoStateLatest);

        // redis에서 삭제
        textMemoStateDao.deleteTextMemoStateLatestFromRedis(individualVideoId);
    }

    // dynamo db에 textMemoStateHistory문 insert
    // save시 redis에서 delete
    @Transactional
    public void saveTextMemoStateHistoryToDynamoDb(String individualVideoId){

        // text_memo_state_history를 redis에서 get
        List<TextMemoStateHistory> historyListFromRedis = textMemoStateDao.getTextMemoStateHistoryFromRedis(individualVideoId);

        // get된 text_memo_state_history 다이나모db에 save
        textMemoStateDao.saveHistoryListToDynamo(historyListFromRedis);

        // redis에서 삭제
        textMemoStateDao.deleteTextMemoStateHistoryFromRedis(individualVideoId);
    }

    /*
    old version
     */

    // redis에 textMemoState 객체 리스트 저장 메소드
//    public void saveTextMemoStatesToRedis(List<TextMemoStateRedisSaveRequest> textMemoStates){
//
//        textMemoStateDao.saveListToRedis(textMemoStates);
//    }


}
