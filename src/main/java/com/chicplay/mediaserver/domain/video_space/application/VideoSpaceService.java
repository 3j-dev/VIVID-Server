package com.chicplay.mediaserver.domain.video_space.application;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.dto.UserGetResponse;
import com.chicplay.mediaserver.domain.user.exception.UserAccessDeniedException;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.dto.HostedVideoGetResponse;
import com.chicplay.mediaserver.domain.video.dto.VideoGetResponse;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceDao;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.HostedVideoSpaceGetResponse;
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

    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    private final VideoSpaceDao videoSpaceDao;

    private final UserService userService;

    // 로그인한 account의 video space, video get list get 메소드
    public List<VideoSpaceGetResponse> getList() {

        // account get by access token
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

                if (!videoSpaceGetResponseHashMap.containsKey(individualVideo.getVideo().getId()))
                    return;

                VideoGetResponse videoGetResponse = videoSpaceGetResponseHashMap.get(individualVideo.getVideo().getId());
                videoGetResponse.changeIndividualVideoState(individualVideo.getId().toString(), individualVideo.getLastAccessTime(), individualVideo.getProgressRate());
                videoSpaceGetResponse.addVideo(videoGetResponse);
            });

            videoSpaceGetResponseList.add(videoSpaceGetResponse);
        });

        return videoSpaceGetResponseList;
    }

    // 자신이 생성한(host인) video space list를 get합니다.
    public List<HostedVideoSpaceGetResponse> getHostedList() {

        // email get, = host email
        String email = userService.getEmailFromAuthentication();

        // find by host email
        List<VideoSpace> videoSpaces = videoSpaceRepository.findAllByHostEmail(email);

        List<HostedVideoSpaceGetResponse> hostedVideoSpaceGetResponseList = new ArrayList<>();

        // size 0일 경우 exception
        if (videoSpaces.size() == 0 || videoSpaces == null)
            return hostedVideoSpaceGetResponseList;

        // video space마다 response dto 생성
        videoSpaces.forEach(videoSpace -> {

            HostedVideoSpaceGetResponse hostedVideoSpaceGetResponse = HostedVideoSpaceGetResponse.builder().videoSpace(videoSpace).build();

            // create video response dto
            videoSpace.getVideos().forEach(video -> {
                hostedVideoSpaceGetResponse.addVideoGetResponse(HostedVideoGetResponse.builder()
                        .id(video.getId())
                        .title(video.getTitle())
                        .description(video.getDescription()).build());
            });

            // create user response dto
            videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
                User user = videoSpaceParticipant.getUser();
                hostedVideoSpaceGetResponse.addUserGetResponse(UserGetResponse.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .picture(user.getPicture())
                        .build());
            });

            hostedVideoSpaceGetResponseList.add(hostedVideoSpaceGetResponse);
        });

        return hostedVideoSpaceGetResponseList;
    }


    // video space save, 생성시 생성자에 대해서 participant 자동 생성
    public VideoSpaceSaveResponse save(VideoSpaceSaveRequest videoSpaceSaveRequest) {

        // account find
        User user = userService.findByAccessToken();

        // video space 생성
        VideoSpace savedVideoSpace = videoSpaceRepository.save(videoSpaceSaveRequest.toEntity(user.getEmail()));

        // 생성자가 포함된 video space participant create, 연관 관계 매핑에 의해 생성된다.

        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(savedVideoSpace).user(user).build();
        savedVideoSpace.getVideoSpaceParticipants().add(videoSpaceParticipant);

        return VideoSpaceSaveResponse.builder().videoSpace(savedVideoSpace).build();
    }

    // fetch join find
    public VideoSpace findById(Long id) {

        // id를 통해 videoSpace get
        Optional<VideoSpace> videoSpace = videoSpaceRepository.findById(id);

        // not found exception
        videoSpace.orElseThrow(() -> new VideoSpaceNotFoundException(id.toString()));

        return videoSpace.get();
    }

    public void delete(Long videoSpaceId) {

        User user = userService.findByAccessToken();

        VideoSpace videoSpace = findById(videoSpaceId);

        // 자신이 host인 경우만 삭제 가능, throw new
        if(!videoSpace.getHostEmail().equals(user.getEmail()))
            throw new UserAccessDeniedException();

        // 연관 관계 끊기.
        videoSpace.remove();

        // space delete
        videoSpaceRepository.deleteById(videoSpaceId);

    }
}
