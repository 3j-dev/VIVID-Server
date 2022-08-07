package com.chicplay.mediaserver.domain.individual_video.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "text_memo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextMemo {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "text_memo_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "individual_video_id")
    private IndividualVideo individualVideo;

    @Email
    @Column(name = "text")
    private String text;

    @Column(name = "video_time")
    private LocalDateTime videoTime;
}
