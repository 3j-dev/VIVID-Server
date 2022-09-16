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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_space_id")
    private VideoSpace videoSpace;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
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
}
