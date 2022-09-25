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
public class UserNewTokenReqeust {

    @NotBlank
    private String accessToken;

    @Builder
    public UserNewTokenReqeust(String accessToken) {
        this.accessToken = accessToken;
    }
}
