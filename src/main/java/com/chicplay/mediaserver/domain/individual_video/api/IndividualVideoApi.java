package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateHistory;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoStateLatest;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateResponse;
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


}
