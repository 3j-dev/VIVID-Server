package com.chicplay.mediaserver.domain.video.domain;

import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTime{

    @Id
    @Column(name = "video_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
