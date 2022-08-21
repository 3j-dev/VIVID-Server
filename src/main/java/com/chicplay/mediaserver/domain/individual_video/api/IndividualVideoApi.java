package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IndividualVideoApi {

    private final IndividualVideoService individualVideoService;

    @PostMapping("/api/individuals/video")
    public void saveTextMemoState(@RequestBody @Valid final TextMemoStateSaveRequest dto){

        individualVideoService.saveTextMemoState(dto);
    }

}
