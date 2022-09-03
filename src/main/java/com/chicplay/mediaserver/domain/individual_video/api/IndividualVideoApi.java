package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
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

    // redis cache에 state save 메소드
    // save할 때는 latest, history의 구분 없이 input을 받고, dao 파트에서 나눠서 저장한다.
    @PostMapping("/cache/text-memo-state")
    public void saveTextMemoStateToCache(@RequestBody @Valid final TextMemoStateRedisSaveRequest dto){

        individualVideoService.saveTextMemoStateToRedis(dto);
    }

    // redis 캐시로 부터 text memo state latest get
    // redis에 데이터가 없다면(만료됐다면) dynamoDB에서 get한다.
    @GetMapping("/cache/text-memo-state-latest")
    public TextMemoStateLatest getTextMemoStateFromCache(@RequestBody HashMap<String, String> request ){
        return individualVideoService.getTextMemoStateLatestFromRedis(request.get("individualVideoId"));
    }


    // dynamodb에 latest,history 모두 save
    @PostMapping("/text-memo-states")
    public void saveTextMemoStatesToDynamoDb(@RequestBody @Valid HashMap<String, String> request ){

        // latest save
        individualVideoService.saveTextMemoStateLatestToDynamoDb(request.get("individualVideoId"));

        // history save
        individualVideoService.saveTextMemoStateHistoryToDynamoDb(request.get("individualVideoId"));
    }

    // dynamoDB에 text state latest문 저장 메소드
    @PostMapping("/text-memo-state-latest")
    public void saveTextMemoStateLatestToDynamoDb(@RequestBody @Valid HashMap<String, String> request){

        individualVideoService.saveTextMemoStateLatestToDynamoDb(request.get("individualVideoId"));
    }

    // dynamoDB에 text state history문 저장 메소드.
    @PostMapping("/text-memo-state-history")
    public void saveTextMemoStatesHistoryToDynamoDb(@RequestBody @Valid HashMap<String, String> request ){

        individualVideoService.saveTextMemoStateHistoryToDynamoDb(request.get("individualVideoId"));
    }


    /*
    old version
     */

    // redis 캐시에 텍스트 메모 스테이트 리스트를 저장하는 메소드
//    @PostMapping("/cache/text-memo-states")
//    public void saveTextMemoStatesToCache(@RequestBody @Valid final List<TextMemoStateRedisSaveRequest> textMemoStates) {
//
//        individualVideoService.saveTextMemoStatesToRedis(textMemoStates);
//    }
}
