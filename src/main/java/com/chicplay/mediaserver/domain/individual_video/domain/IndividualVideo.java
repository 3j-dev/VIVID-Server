package com.chicplay.mediaserver.domain.individual_video.domain;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "individual_video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndividualVideo extends BaseTime {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "individual_video_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private Video video;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_space_participant_id")
    private VideoSpaceParticipant videoSpaceParticipant;

    @Builder
    public IndividualVideo(Video video) {
        changeVideo(video);
    }

    // 연관 관계 편의 메소드
    public void changeVideo(Video video){

        // 기존의 비디오 관계가 있다면,
        if(this.video != null){
            this.video.getIndividualVideos().remove(this);
        }

        this.video = video;
        this.video.getIndividualVideos().add(this);
    }

}
