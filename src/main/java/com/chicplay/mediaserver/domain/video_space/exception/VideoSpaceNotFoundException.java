package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class VideoSpaceNotFoundException extends EntityNotFoundException {

    public VideoSpaceNotFoundException() {
        super(ErrorCode.VIDEO_SPACE_NOT_FOUND);
    }
}
