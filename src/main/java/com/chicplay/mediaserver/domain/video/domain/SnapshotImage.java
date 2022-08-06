package com.chicplay.mediaserver.domain.video.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "snapshot_image")
@Getter
@NoArgsConstructor
public class SnapshotImage {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "snpashot_image_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "individual_class_id", nullable = false, unique = true)
    private Long individualClassId;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "file_path")
    private String filePath;





}
