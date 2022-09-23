package com.chicplay.mediaserver.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@RedisHash("user_auth_token")
public class UserAuthToken {

    @Id
    private String email;
    private String token;
    private String refreshToken;

    @Builder
    public UserAuthToken(String email, String token, String refreshToken) {
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
