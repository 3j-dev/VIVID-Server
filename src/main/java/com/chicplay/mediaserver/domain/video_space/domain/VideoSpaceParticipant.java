package com.chicplay.mediaserver.domain.video_space.domain;

import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video_space_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoSpaceParticipant extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_space_participant_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "videoSpaceParticipant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_space_id")
    private VideoSpace videoSpace;

    @Builder
    public VideoSpaceParticipant(User user, VideoSpace videoSpace) {
        changeAccount(user);
        changeVideoSpace(videoSpace);
    }

    // 연관 관계 편의 메소드
    public void changeAccount(User user) {

        // account 관계가 있다면,
        if(this.user != null){
            this.user.getVideoSpaceParticipants().remove(this);
        }

        this.user = user;
        this.user.getVideoSpaceParticipants().add(this);

    }

    // 연관 관계 편의 메소드
    public void changeVideoSpace(VideoSpace videoSpace) {

        // 기존의 video space 관계가 있다면,
        if(this.videoSpace != null){
            this.videoSpace.getVideoSpaceParticipants().remove(this);
        }

        this.videoSpace = videoSpace;
        this.videoSpace.getVideoSpaceParticipants().add(this);

    }
}
