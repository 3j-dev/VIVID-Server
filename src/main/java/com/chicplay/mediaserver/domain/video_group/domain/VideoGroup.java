package com.chicplay.mediaserver.domain.video_group.domain;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoGroup extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_group_id", updatable = false)
    private Long id;

    @OneToMany(mappedBy = "videoGroup",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoGroupParticipant> videoGroupParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "videoGroup",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;


}
