package com.chicplay.mediaserver.domain.user.dto;

import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserGetResponse {

    private String email;

    private String name;

    private String picture;

    @Builder
    public UserGetResponse(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

}
