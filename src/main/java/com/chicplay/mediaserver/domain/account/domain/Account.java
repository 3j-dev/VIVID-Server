package com.chicplay.mediaserver.domain.account.domain;

import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTime {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
