package com.chicplay.mediaserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserNewTokenRequest {

    @NotBlank
    private String accessToken;

    @Builder
    public UserNewTokenRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
