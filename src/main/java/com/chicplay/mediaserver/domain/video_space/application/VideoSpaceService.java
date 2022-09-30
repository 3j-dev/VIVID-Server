package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceDao;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceGetResponse;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveRequest;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceSaveResponse;
import com.chicplay.mediaserver.domain.video_space.exception.VideoSpaceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceService {

    private final VideoSpaceRepository videoSpaceRepository;

    private final VideoSpaceDao videoSpaceDao;

    private final UserService userService;

    // 로그인한 account의 video space, video get list get 메소드
    public List<VideoSpaceGetResponse> read(HttpServletRequest request) {

        // account get by email
        User user = userService.findByEmail(request);

        List<VideoSpaceGetResponse> videoSpaceReadResponse = new ArrayList<>();

        user.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
            videoSpaceReadResponse.add(VideoSpaceGetResponse.builder().videoSpaceParticipant(videoSpaceParticipant).build());
        });

        return videoSpaceReadResponse;
    }


    // video space save, 생성시 생성자에 대해서 participant 자동 생성
    public VideoSpaceSaveResponse save(VideoSpaceSaveRequest videoSpaceSaveRequest,HttpServletRequest request) {

        // account find
        User user = userService.findByEmail(request);

        // video space 생성
        VideoSpace savedVideoSpace = videoSpaceRepository.save(videoSpaceSaveRequest.toEntity());

        // 생성자가 포함된 video space participant create, 연관 관계 매핑에 의해 생성된다.
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(savedVideoSpace).user(user).build();

        return VideoSpaceSaveResponse.builder().videoSpace(savedVideoSpace).build();
    }

    // fetch join find
    public VideoSpace findById(Long id) {

        // id를 통해 videoSpace get
        Optional<VideoSpace> videoSpace = videoSpaceRepository.findById(id);
        //VideoSpace videoSpace = videoSpaceDao.findById(id);

        // not found exception
        videoSpace.orElseThrow(() -> new VideoSpaceNotFoundException(id.toString()));

        return videoSpace.get();
    }


}
