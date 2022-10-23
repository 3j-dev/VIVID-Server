package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.individual_video.dto.DashboardIndividualVideoDto;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.dto.UserMyPageDashboardDataGetResponse;
import com.chicplay.mediaserver.domain.video.dto.VideoGetResponse;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceGetResponse;
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
public class UserMyPageService {

    private final UserService userService;

    private final VideoSpaceService videoSpaceService;

    // dashboard의 user data를 get합니다.
    public UserMyPageDashboardDataGetResponse getMyPageDashboardData() {

        // account get by access token
        User user = userService.getByAccessToken();

        // user video space get, inner video data get
        List<VideoSpaceGetResponse> videoSpaces = videoSpaceService.getList();

        DashboardIndividualVideoDto lastStudiedIndividualVideo = null;
        Long videoSpaceCount = 0L;
        Long totalIndividualVideoCount = 0L;
        Long completedIndividualVideoCount = 0L;

        List<DashboardIndividualVideoDto> dashboardIndividualVideos = new ArrayList<>();

        // 각각의 individual video를 create
        for (VideoSpaceGetResponse videoSpace : videoSpaces) {

            // video space cnt ++
            videoSpaceCount += 1;

            for (VideoGetResponse video : videoSpace.getVideos()) {

                // totla video cnt ++
                totalIndividualVideoCount += 1;

                // completed video ++
                if(video.getProgressRate() == 100L)
                    completedIndividualVideoCount += 1;

                DashboardIndividualVideoDto individualVideo = DashboardIndividualVideoDto.builder()
                        .video(video)
                        .videoSpace(videoSpace)
                        .build();

                // lasted studied individual video
                if(user.getLastAccessIndividualVideoId() != null
                        && user.getLastAccessIndividualVideoId().toString().equals(individualVideo.getIndividualVideoId()))
                    lastStudiedIndividualVideo = individualVideo;

                // 각각의 individual video dto로 add
                dashboardIndividualVideos.add(individualVideo);
            }
        }

        dashboardIndividualVideos.sort((o1,o2) -> o2.getLastAccessTime().compareTo(o1.getLastAccessTime()));

        UserMyPageDashboardDataGetResponse userMyPageDashboardDataGetResponse = UserMyPageDashboardDataGetResponse.builder()
                .user(user)
                .lastStudiedIndividualVideo(lastStudiedIndividualVideo)
                .videoSpaceCount(videoSpaceCount)
                .dashboardIndividualVideos(dashboardIndividualVideos)
                .totalIndividualVideoCount(totalIndividualVideoCount)
                .completedIndividualVideoCount(completedIndividualVideoCount)
                .build();

        return userMyPageDashboardDataGetResponse;
    }
}
