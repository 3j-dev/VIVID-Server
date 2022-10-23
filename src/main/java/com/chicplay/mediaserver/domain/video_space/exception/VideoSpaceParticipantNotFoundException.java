package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class VideoSpaceParticipantNotFoundException extends EntityNotFoundException {

    public VideoSpaceParticipantNotFoundException() {
        super(ErrorCode.VIDEO_SPACE_PARTICIPANT_NOT_FOUND);
    }
}
