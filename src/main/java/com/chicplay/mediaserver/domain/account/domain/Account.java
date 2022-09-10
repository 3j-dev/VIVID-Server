package com.chicplay.mediaserver.domain.account.domain;

import com.chicplay.mediaserver.domain.video_group.domain.VideoGroupParticipant;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTime {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "account_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoGroupParticipant> videoGroupParticipants = new ArrayList<>();

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Embedded
    private Password password;

    @Embedded
    private Institution institution;

    @Column(name="name", nullable = false)
    private String name;


    @Builder
    public Account(String email, Password password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
