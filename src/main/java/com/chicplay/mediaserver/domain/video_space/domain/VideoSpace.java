package com.chicplay.mediaserver.domain.video_space.domain;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.global.common.BaseTime;
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
public class VideoSpace extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_space_id", updatable = false)
    private Long id;

    @OneToMany(mappedBy = "videoSpace",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoSpaceParticipant> videoSpaceParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "videoSpace",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Builder
    public VideoSpace(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
