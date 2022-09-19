package com.chicplay.mediaserver.domain.user.dto;

import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.domain.Password;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserSignUpRequest {

    @NotBlank
    @Email(message = "이메일을 양식을 지켜주세요.")
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;


    @Builder
    public UserSignUpRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .role(Role.USER)
                //.password(Password.builder().password(this.password).build())
                .build();
    }

}
