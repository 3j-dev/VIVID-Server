package com.chicplay.mediaserver.domain.video_space.domain;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_space_id")
    private VideoSpace videoSpace;

    @OneToOne(mappedBy = "videoSpaceParticipant")
    private IndividualVideo individualVideo;

    @Builder
    public VideoSpaceParticipant(Account account, VideoSpace videoSpace) {
        changeAccount(account);
        changeVideoSpace(videoSpace);
    }

    // 연관 관계 편의 메소드
    public void changeAccount(Account account) {

        // account 관계가 있다면,
        if(this.account != null){
            this.account.getVideoSpaceParticipants().remove(this);
        }

        this.account = account;
        this.account.getVideoSpaceParticipants().add(this);

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
