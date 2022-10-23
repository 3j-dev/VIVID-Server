package com.chicplay.mediaserver.domain.video.domain;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTime{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_space_id", nullable = false)
    private VideoSpace videoSpace;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="uploader_id")
    private String uploaderId;

    @Column(name = "is_uploaded")
    private boolean isUploaded;

    public void changeIsUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }


    @Builder
    public Video(VideoSpace videoSpace, String title, String description,String uploaderId) {
        this.videoSpace = videoSpace;
        this.title = title;
        this.description = description;
        this.uploaderId = uploaderId;
        this.isUploaded = false;
    }

    // 연관 관계 편의 메소드
    public void changeVideo(VideoSpace videoSpace){

        // 기존의 비디오 관계가 있다면,
        if(this.videoSpace != null){
            this.videoSpace.getVideos().remove(this);
        }

        this.videoSpace = videoSpace;
        this.videoSpace.getVideos().add(this);

    }

    // 전체 연관 관계 삭제 편의 메소드
    public void delete() {

        // OneToMany 연관관계 삭제
        videoSpace.getVideos().remove(this);

        // ManyToOne 연관관계 삭제
        deleteMapping();

        // OnneToMany 연관관계 삭제
        for (IndividualVideo individualVideo : individualVideos) {
            individualVideo.getVideoSpaceParticipant().getIndividualVideos().remove(individualVideo);
            individualVideo.deleteMapping();
        }

        // ManyToOne 연관관계 삭제
        individualVideos.clear();
    }

    // ManyToOne 연관관계(부모) 삭제
    public void deleteMapping() {
        this.videoSpace = null;

//        // individualVideo와 연관관계 끊기
//        for (IndividualVideo individualVideo : individualVideos) {
//            individualVideo.deleteMapping();
//        }
    }
}
