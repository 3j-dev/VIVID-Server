package com.chicplay.mediaserver.domain.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Institution {

    @Column(name = "zoom_id")
    private String zoomId;

    @Column(name = "webex_access_token")
    private String webexAccessToken;

    public void changeWebexAccessToken(String webexAccessToken) {
        this.webexAccessToken = webexAccessToken;
    }
}
