package com.chicplay.mediaserver.domain.user.dto;

import com.chicplay.mediaserver.domain.individual_video.dto.DashboardIndividualVideoDto;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoGetResponse;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.video.dto.VideoGetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserMyPageDashboardDataGetResponse {

    private String email;

    private String name;

    private String picture;

    private boolean isConnectedWebex;

    private DashboardIndividualVideoDto lastStudiedIndividualVideo;

    private Long videoSpaceCount;

    private Long totalIndividualVideoCount;

    private Long completedIndividualVideoCount;

    private List<DashboardIndividualVideoDto> dashboardIndividualVideos = new ArrayList<>();

    @Builder
    public UserMyPageDashboardDataGetResponse(User user, DashboardIndividualVideoDto lastStudiedIndividualVideo,List<DashboardIndividualVideoDto> dashboardIndividualVideos,
                                              Long videoSpaceCount, Long totalIndividualVideoCount, Long completedIndividualVideoCount) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.picture = user.getPicture();
        this.isConnectedWebex = user.getInstitution().getWebexAccessToken() == null ? false : true;
        this.lastStudiedIndividualVideo = lastStudiedIndividualVideo;
        this.dashboardIndividualVideos = dashboardIndividualVideos;

        this.videoSpaceCount = videoSpaceCount;
        this.totalIndividualVideoCount = totalIndividualVideoCount;
        this.completedIndividualVideoCount = completedIndividualVideoCount;
    }
}
