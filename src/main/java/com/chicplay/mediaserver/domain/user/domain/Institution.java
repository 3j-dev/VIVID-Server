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

    @Column(name = "webex_id")
    private String webexId;

    @Column(name = "elice_id")
    private String eliceId;
}
