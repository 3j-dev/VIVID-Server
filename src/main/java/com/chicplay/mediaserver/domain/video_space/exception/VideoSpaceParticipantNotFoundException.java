package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

public class VideoSpaceParticipantNotFoundException extends EntityNotFoundException {

    public VideoSpaceParticipantNotFoundException() {
        super("VideoSpaceParticipant is not found");
    }
}
