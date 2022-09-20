package com.chicplay.mediaserver.domain.user.dto;

import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.User;
import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank
    @Email(message = "이메일을 양식을 지켜주세요.")
    private String email;

    private String name;

    @Builder
    public UserLoginRequest(String email, String name) {
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .role(Role.USER)
                .build();
    }

}
