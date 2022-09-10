package com.chicplay.mediaserver.domain.video_group.domain;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "video_group_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoGroupParticipant extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_group_participant_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_group_id")
    private VideoGroup videoGroup;

    @OneToOne(mappedBy = "videoGroupParticipant")
    private IndividualVideo individualVideo;


}
