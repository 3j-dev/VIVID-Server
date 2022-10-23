package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.AccessDeniedException;
import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class VideoSpaceHostedAccessRequiredException extends AccessDeniedException {

    public VideoSpaceHostedAccessRequiredException() {
        super(ErrorCode.VIDEO_SPACE_HOST_ACCESS_REQUIRED);
    }
}
