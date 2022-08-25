package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/individuals-videos")
public class IndividualVideoApi {

    private final IndividualVideoService individualVideoService;

    // redis 캐시에 단일 메모 스테이트를 저장하는 메소드
    @PostMapping("/cache/text-memo-state")
    public void saveTextMemoState(@RequestBody @Valid final TextMemoStateSaveRequest dto){

        individualVideoService.saveTextMemoStateToRedis(dto);
    }

    // redis 캐시에 텍스트 메모 스테이트 리스트를 저장하는 메소드
    @PostMapping("/cache/text-memo-states")
    public void saveTextMemoStates(@RequestBody @Valid final List<TextMemoStateSaveRequest> textMemoStates) {

        individualVideoService.saveTextMemoStatesToRedis(textMemoStates);
    }

}
