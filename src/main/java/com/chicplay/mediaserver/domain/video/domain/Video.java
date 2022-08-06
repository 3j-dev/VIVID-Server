package com.chicplay.mediaserver.domain.video.domain;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTime{


    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "video_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(mappedBy = "video",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    @Column(name="file_path", nullable = false)
    private String filePath;

    @Column(name="uploader_id", nullable = false)
    private String uploaderId;

    @Column(name="chatting_json_file_path")
    private String chattingJsonFilePath;

    @Column(name="video_indexing_image_file_path")
    private String videoIndexingImageFilePath;


    @Builder
    public Video(String filePath, String uploaderId, String chattingJsonFilePath, String videoIndexingImageFilePath) {
        this.filePath = filePath;
        this.uploaderId = uploaderId;
        this.chattingJsonFilePath = chattingJsonFilePath;
        this.videoIndexingImageFilePath = videoIndexingImageFilePath;
    }
}
