package com.chicplay.mediaserver.domain.video_space.domain;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video_space")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoSpace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_space_id", updatable = false)
    private Long id;

    @OneToMany(mappedBy = "videoSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoSpaceParticipant> videoSpaceParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "videoSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "host_email")
    private String hostEmail;

    @Column(name = "is_individual_video_space", columnDefinition = "TINYINT(1)")
    private boolean isIndividualVideoSpace;

    @Builder
    public VideoSpace(String name, String description, String hostEmail, boolean isIndividualVideoSpace) {
        this.name = name;
        this.description = description;
        this.hostEmail = hostEmail;
        this.isIndividualVideoSpace = isIndividualVideoSpace;
    }

    // 연관 관계 삭제 편의 메소드
    public void delete() {

        // ManyToOne, videoSpaecParticipant와 연관 관계 끊기
        for (VideoSpaceParticipant videoSpaceParticipant : videoSpaceParticipants) {
            videoSpaceParticipant.deleteMapping();
        }

        // One to Many 연관 관계 끊기.
        videoSpaceParticipants.clear();

        // ManyToOne, video와 연관 관계 끊기
        for (Video video : videos) {
            video.deleteMapping();
        }

        // One to Many 연관 관계 끊기.
        videos.clear();
    }

}
