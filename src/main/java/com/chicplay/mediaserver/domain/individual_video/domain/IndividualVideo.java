package com.chicplay.mediaserver.domain.individual_video.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "individual_video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndividualVideo {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "individual_id", columnDefinition = "BINARY(16)")
    private UUID id;
}
