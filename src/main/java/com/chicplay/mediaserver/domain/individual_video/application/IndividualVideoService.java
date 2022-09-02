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

    @Transactional
    public TextMemoStateLatest getTextMemoStateLatestFromRedis(String individualVideoId){

        return textMemoStateDao.findTextMemoStateLatestFromRedis(individualVideoId);
    }


    // redis의 state latest 다이나모 db에 저장.
    @Transactional
    public void saveTextMemoStateLatestToDynamoDb(String individualVideoId){

        // 요청된 individualVideoId의 state latest를 redis에서 가져온다.
        TextMemoStateLatest textMemoStateLatest = textMemoStateDao.findTextMemoStateLatestFromRedis(individualVideoId);

        // 해당 state를 저장한다.
        textMemoStateDao.saveLatestToDynamo(textMemoStateLatest);
    }

    // redis에 textMemoState 객체 리스트 저장 메소드
    public void saveTextMemoStatesToRedis(List<TextMemoStateRedisSaveRequest> textMemoStates){

        textMemoStateDao.saveListToRedis(textMemoStates);
    }



    // dynamo db에 textMemoStateHistory문 insert
    @Transactional
    public void saveTextMemoStateHistoryToDynamoDb(String individualVideoId){

        // text_memo_state_history를 redis에서 get
        List<TextMemoStateHistory> historyListFromRedis = textMemoStateDao.getTextMemoStateHistoryFromRedis(individualVideoId);

        // get된 text_memo_state_history 다이나모db에 save
        textMemoStateDao.saveHistoryListToDynamo(historyListFromRedis);
    }


}
