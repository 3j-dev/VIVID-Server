package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.exception.IndividualVideoNotFoundException;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.dto.VideoGetResponse;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<VideoSpaceGetResponse> getList() {

        // account get by email
        User user = userService.findByAccessToken();

        List<VideoSpaceGetResponse> videoSpaceGetResponseList = new ArrayList<>();

        // user가 참여해 있는 video space get
        user.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {

            VideoSpace videoSpace = videoSpaceParticipant.getVideoSpace();

            List<Video> videoList = videoSpace.getVideos();

            // response dto 생성, 해당 dto에 video 리스트가 포함된다.
            VideoSpaceGetResponse videoSpaceGetResponse = VideoSpaceGetResponse.builder()
                    .videoSpaceParticipant(videoSpaceParticipant)
                    .videoSpace(videoSpace)
                    .build();

            HashMap<Long, VideoGetResponse> videoSpaceGetResponseHashMap = new HashMap<>();

            // 각각 video에 대해 individual video id랑 매칭하기 위해 hash map으로 생성
            videoList.forEach(video -> {

                // video 마다 dto 생성, 해당 dto는 individual_video_id도 포함한다.
                VideoGetResponse videoGetResponse = VideoGetResponse.builder()
                        .id(video.getId())
                        .title(video.getTitle())
                        .description(video.getDescription())
                        .build();

                videoSpaceGetResponseHashMap.put(video.getId(), videoGetResponse);
            });

            // individual video id add
            videoSpaceParticipant.getIndividualVideos().forEach(individualVideo -> {

                if(!videoSpaceGetResponseHashMap.containsKey(individualVideo.getVideo().getId()))
                    return;

                VideoGetResponse videoGetResponse = videoSpaceGetResponseHashMap.get(individualVideo.getVideo().getId());
                videoGetResponse.changeIndividualVideoId(individualVideo.getId().toString());
                videoSpaceGetResponse.addVideoGetResponse(videoGetResponse);
            });

            videoSpaceGetResponseList.add(videoSpaceGetResponse);
        });

        return videoSpaceGetResponseList;
    }


    // video space save, 생성시 생성자에 대해서 participant 자동 생성
    public VideoSpaceSaveResponse save(VideoSpaceSaveRequest videoSpaceSaveRequest) {

        // account find
        User user = userService.findByAccessToken();

        // video space 생성
        VideoSpace savedVideoSpace = videoSpaceRepository.save(videoSpaceSaveRequest.toEntity());

        // 생성자가 포함된 video space participant create, 연관 관계 매핑에 의해 생성된다.

        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(savedVideoSpace).user(user).build();
        savedVideoSpace.getVideoSpaceParticipants().add(videoSpaceParticipant);

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
