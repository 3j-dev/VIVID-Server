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
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "individual_video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndividualVideo extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "individual_video_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_space_participant_id", nullable = false)
    private VideoSpaceParticipant videoSpaceParticipant;

    @Column(name = "progress_rate")
    private Long progressRate;

    @Column(name = "last_access_time")
    private LocalDateTime lastAccessTime;

    @Builder
    public IndividualVideo(Video video, VideoSpaceParticipant videoSpaceParticipant) {
        changeVideo(video);
        changeVideoSpaceParticipant(videoSpaceParticipant);
        this.progressRate = 0L;
        this.lastAccessTime = LocalDateTime.now();
    }

    // 최종 접근 시간 변경 메소드
    public void changeLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
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

    // 연관 관계 편의 메소드
    public void changeVideoSpaceParticipant(VideoSpaceParticipant videoSpaceParticipant) {

        if(this.videoSpaceParticipant != null){
            this.videoSpaceParticipant.getIndividualVideos().remove(this);
        }

        this.videoSpaceParticipant = videoSpaceParticipant;
        this.videoSpaceParticipant.getIndividualVideos().add(this);
    }

    public void delete() {

        // ManyToOne 연관관계 끊기.
        videoSpaceParticipant.getIndividualVideos().remove(this);
        video.getIndividualVideos().remove(this);

        // OneToMany 연관관계 끊기.
        deleteMapping();

    }

    public void deleteMapping() {
        video = null;
        videoSpaceParticipant = null;
    }


}
