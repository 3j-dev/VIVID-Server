package com.chicplay.mediaserver.domain.video.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

import java.util.UUID;

public class VideoNotFoundException extends EntityNotFoundException {

    public VideoNotFoundException(UUID id) {
        super(id.toString() + " is not found");
    }
}
