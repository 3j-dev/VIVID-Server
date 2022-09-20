package com.chicplay.mediaserver.global.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJwt {
    private String token;
    private String refreshToken;

    @Builder
    public UserJwt(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
