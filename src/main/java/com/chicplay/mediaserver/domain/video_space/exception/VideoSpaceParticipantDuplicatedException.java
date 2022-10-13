package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class VideoSpaceParticipantDuplicatedException extends BusinessException {

    public VideoSpaceParticipantDuplicatedException() {
        super(ErrorCode.VIDEO_SPACE_USER_DUPLICATION);
    }
}
