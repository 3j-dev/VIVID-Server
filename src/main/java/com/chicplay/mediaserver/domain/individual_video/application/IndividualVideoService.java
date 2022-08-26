package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateRepository;
import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateDao;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndividualVideoService {

    private final TextMemoStateRepository textMemoStateRepository;

    private final TextMemoStateDao textMemoStateDao;


    // redis에 textMemoState 객체 하나 저장 메소드
    @Transactional
    public void saveTextMemoStateToRedis(final TextMemoStateSaveRequest textMemoState){

        textMemoStateDao.saveToRedis(textMemoState.toEntity());
    }

    // redis에 textMemoState 객체 리스트 저장 메소드
    public void saveTextMemoStatesToRedis(List<TextMemoStateSaveRequest> textMemoStates){

        textMemoStateDao.saveListToRedis(textMemoStates);
    }

    // dynamo db에 textMemoState문 insert
    public void saveTextMemoStateToDynamoDb(TextMemoStateSaveRequest textMemoState){

        textMemoStateDao.saveToDynamo(textMemoState.toEntity());
    }



}
