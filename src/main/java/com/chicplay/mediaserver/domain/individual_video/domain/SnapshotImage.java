package com.chicplay.mediaserver.domain.individual_video.domain;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "snapshot_image")
@Getter
@NoArgsConstructor
public class SnapshotImage extends BaseTime {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "snapshot_image_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "individual_video_id")
    private IndividualVideo individualVideo;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "video_time")
    private LocalTime videoTime;

    @Builder
    public SnapshotImage(IndividualVideo individualVideo, String filePath, LocalTime videoTime) {
        this.individualVideo = individualVideo;
        this.filePath = filePath;
        this.videoTime = videoTime;
    }
}
