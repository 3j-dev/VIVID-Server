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

    @NotBlank
    private String name;

    private String picture;

    @Builder
    public UserLoginRequest(String email, String name,String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .role(Role.USER)
                .build();
    }

}
