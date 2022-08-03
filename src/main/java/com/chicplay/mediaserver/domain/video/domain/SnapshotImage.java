package com.chicplay.mediaserver.domain.video.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

@Entity
@Table(name = "snapshot_image")
@Getter
@NoArgsConstructor
public class SnapshotImage {

    @Id
    @Column(name = "snapshot_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "individual_class_id", nullable = false, unique = true)
    private Long individualClassId;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "file_path")
    private String filePath;





}
