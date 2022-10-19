package com.chicplay.mediaserver.domain.video_space.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

public class VideoSpaceNotFoundException extends EntityNotFoundException {

    public VideoSpaceNotFoundException(String id) {
        super(id + " is not found");
    }
}
