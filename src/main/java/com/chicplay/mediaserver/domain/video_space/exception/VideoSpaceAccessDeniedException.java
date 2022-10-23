package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.AccessDeniedException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class VideoSpaceAccessDeniedException extends AccessDeniedException {

    public VideoSpaceAccessDeniedException() {
        super(ErrorCode.VIDEO_SPACE_ACCESS_DENIED);
    }
}
