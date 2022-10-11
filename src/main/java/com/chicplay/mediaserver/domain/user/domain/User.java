package com.chicplay.mediaserver.domain.user.domain;

import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoSpaceParticipant> videoSpaceParticipants = new ArrayList<>();

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Embedded
    private Institution institution = new Institution();

    @Column(name="name", nullable = false)
    private String name;


    @Column(name="picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;


//    @Builder
//    public User(String email, Password password, String name) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//    }

    @Builder
    public User(String email,  String name, String picture, Role role) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.picture = picture;
    }

    // null 체크를 위한 getter 따로 생성
    public Institution getInstitution() {
        return this.institution == null ? new Institution() : this.institution;
    }

    public void changeInstitution(Institution institution) {
        this.institution = institution;
    }



}
