package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateHistory;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class TextMemoStateApi {

    private final IndividualVideoService individualVideoService;

    // redis cache에 state save 메소드
    // save할 때는 latest, history의 구분 없이 input을 받고, dao 파트에서 나눠서 저장한다.
    @Operation(summary = "text state 레디스 save 메소드", description = "text state를 레디스 캐시에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/cache/text-memo-state")
    public void saveTextMemoStateToCache(@RequestBody @Valid final TextMemoStateRedisSaveRequest dto) {

        individualVideoService.saveTextMemoStateToRedis(dto);
    }

    // dynamodb에 latest,history 모두 save
    @Operation(summary = "text state 다이나모DB save 메소드", description = "레디스 캐시에 있는 text state 모두를 다이나모DB에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/text-memo-states")
    public void saveTextMemoStatesToDynamoDb(@PathVariable("individual-video-id") String individualVideoId) {

        // latest save
        individualVideoService.saveTextMemoStateLatestToDynamoDb(individualVideoId);

        // history save
        individualVideoService.saveTextMemoStateHistoryToDynamoDb(individualVideoId);
    }

    // dynamoDB에 text state latest문 저장 메소드
    @Operation(summary = "text state latest 다이나모DB save 메소드", description = "레디스 캐시에 있는 text state latest를 다이나모DB에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/text-memo-state-latest")
    public void saveTextMemoStateLatestToDynamoDb(@PathVariable("individual-video-id") String individualVideoId) {

        individualVideoService.saveTextMemoStateLatestToDynamoDb(individualVideoId);
    }

    // dynamoDB에 text state history문 저장 메소드.
    @Operation(summary = "text state history 다이나모DB save 메소드", description = "레디스 캐시에 있는 text state history 전부를 다이나모DB에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/text-memo-state-history")
    public void saveTextMemoStatesHistoryToDynamoDb(@PathVariable("individual-video-id") String individualVideoId) {

        individualVideoService.saveTextMemoStateHistoryToDynamoDb(individualVideoId);
    }

    // redis 캐시로 부터 text memo state latest get
    // redis에 데이터가 없다면(만료됐다면) dynamoDB에서 get한다.
    @Operation(summary = "text state latest get 메소드", description = "레디스 캐시에 있는 text state latest를 get합니다. 캐시에 없을시 다이나모DB에서 get합니다.")
    @ApiResponse(responseCode = "200", description = "text state latest을 json 형식으로 반환합니다.")
    @GetMapping("/{individual-video-id}/cache/text-memo-state-latest")
    public TextMemoStateResponse getTextMemoStateFromCache(@PathVariable("individual-video-id") String individualVideoId) {

        TextMemoStateLatest textMemoStateLatest = individualVideoService.getTextMemoStateLatestFromRedis(individualVideoId);

        return TextMemoStateResponse.builder().textMemoState(textMemoStateLatest).build();
    }

    // redis 캐시로 부터 text memo state latest get
    // redis에 데이터가 없다면(만료됐다면) dynamoDB에서 get한다.
    @Operation(summary = "text state history list get 메소드", description = "다이나모DB 에 있는 text state history list를 get합니다.")
    @ApiResponse(responseCode = "200", description = "text state history를 json list 형식으로 반환합니다.")
    @GetMapping("/{individual-video-id}/text-memo-state-history")
    public List<TextMemoStateResponse> getTextMemoStateHistoryFromDynamoDb(@PathVariable("individual-video-id") String individualVideoId) {

        List<TextMemoStateHistory> textMemoStateHistoryList = individualVideoService.getTextMemoStateHistoryFromDynamoDb(individualVideoId);

        // dto로 객체 변환
        List<TextMemoStateResponse> textMemoStateResponseList = new ArrayList<>();
        textMemoStateHistoryList.forEach(textMemoStateHistory -> {
            textMemoStateResponseList.add(TextMemoStateResponse.builder().textMemoState(textMemoStateHistory).build());
        });

        return textMemoStateResponseList;
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
