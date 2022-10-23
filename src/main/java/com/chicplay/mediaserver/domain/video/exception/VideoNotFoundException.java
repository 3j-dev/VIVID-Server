package com.chicplay.mediaserver.domain.video.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

import java.util.UUID;

public class VideoNotFoundException extends EntityNotFoundException {

    public VideoNotFoundException() {
        super(ErrorCode.VIDEO_NOT_FOUND);
    }

    public VideoNotFoundException(String id) {
        super(id.toString() + " is not found");
    }
}
