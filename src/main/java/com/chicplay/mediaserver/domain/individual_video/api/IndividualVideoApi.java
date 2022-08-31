package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateDynamoSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/individuals-videos")
public class IndividualVideoApi {

    private final IndividualVideoService individualVideoService;

    // redis 캐시에 단일 메모 스테이트를 저장하는 메소드
    @PostMapping("/cache/text-memo-state")
    public void saveTextMemoStateToCache(@RequestBody @Valid final TextMemoStateRedisSaveRequest dto){

        individualVideoService.saveTextMemoStateToRedis(dto);
    }

    // redis 캐시로 부터 text memo state latest get
    @GetMapping("/cache/text-memo-state-latest")
    public TextMemoStateLatest getTextMemoStateFromCache(@RequestBody HashMap<String, String> request ){
        return individualVideoService.getTextMemoStateLatestFromRedis(request.get("individualVideoId"));
    }


    // redis 캐시에 텍스트 메모 스테이트 리스트를 저장하는 메소드
    @PostMapping("/cache/text-memo-states")
    public void saveTextMemoStatesToCache(@RequestBody @Valid final List<TextMemoStateRedisSaveRequest> textMemoStates) {

        individualVideoService.saveTextMemoStatesToRedis(textMemoStates);
    }


//    @PostMapping("/text-memo-states-latest")
//    public void saveTextMemoStatesLatestToDynamoDb(@RequestBody @Valid final TextMemoStateDynamoSaveRequest textMemoState){
//
//        individualVideoService.saveTextMemoStateLatestToDynamoDb(textMemoState);
//    }

    // dynamoDB에 text state latest문 저장 메소드
    @PostMapping("/text-memo-state-latest")
    public void saveTextMemoStatesLatestToDynamoDb(@RequestBody HashMap<String, String> request ){

        individualVideoService.saveTextMemoStateLatestToDynamoDb(request.get("individualVideoId"));
    }

    // dynamoDB에 text state history문 저장 메소드.
    @PostMapping("/text-memo-states-history")
    public void saveTextMemoStatesHistoryToDynamoDb(@RequestBody @Valid final List<TextMemoStateDynamoSaveRequest> textMemoStates){

        individualVideoService.saveTextMemoStateHistoryToDynamoDb(textMemoStates);
    }

}
