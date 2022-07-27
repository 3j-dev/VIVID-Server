package com.chicplay.mediaserver.domain.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Account {

    @Id
    @GeneratedValue
    private long id;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Embedded
    private Password password;

    @Column(name="name", nullable = false)
    private String name;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Builder
    public Account(long id, String email, Password password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
