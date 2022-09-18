package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.account.application.AccountService;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceDao;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceGetResponse;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveRequest;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceService {

    private final VideoSpaceRepository videoSpaceRepository;

    private final VideoSpaceDao videoSpaceDao;

    private final AccountService accountService;

    // 로그인한 account의 video space, video get list get 메소드
    public List<VideoSpaceGetResponse> read() {

        // account get by email
        Account account = accountService.findByEmail();

        List<VideoSpaceGetResponse> videoSpaceReadResponse = new ArrayList<>();

        account.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
            videoSpaceReadResponse.add(VideoSpaceGetResponse.builder().videoSpaceParticipant(videoSpaceParticipant).build());
        });

        return videoSpaceReadResponse;
    }


    // video space save, 생성시 생성자에 대해서 participant 자동 생성
    public VideoSpaceSaveResponse save(VideoSpaceSaveRequest videoSpaceSaveRequest) {

        // account find
        Account account = accountService.findByEmail();

        // video space 생성
        VideoSpace savedVideoSpace = videoSpaceRepository.save(videoSpaceSaveRequest.toEntity());

        // 생성자가 포함된 video space participant create, 연관 관계 매핑에 의해 생성된다.
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(savedVideoSpace).account(account).build();

        return VideoSpaceSaveResponse.builder().videoSpace(savedVideoSpace).build();
    }

    // fetch join find
    public VideoSpace findById(Long id) {

        // id를 통해 videoSpace get
        //Optional<VideoSpace> videoSpace = videoSpaceRepository.findById(id);
        VideoSpace videoSpace = videoSpaceDao.findById(id);

        return videoSpace;
    }


}
