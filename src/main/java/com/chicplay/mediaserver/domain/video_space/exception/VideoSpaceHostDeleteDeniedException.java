package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.AccessDeniedException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class VideoSpaceHostDeleteDeniedException extends AccessDeniedException {

    public VideoSpaceHostDeleteDeniedException() {
        super(ErrorCode.VIDEO_SPACE_HOST_DELETE_NOT_ALLOWED);
    }
}
