package com.chicplay.mediaserver.domain.individual_video.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

import java.util.UUID;

public class IndividualVideoNotFoundException extends EntityNotFoundException {

    public IndividualVideoNotFoundException() {
        super("video is not found");
    }

    public IndividualVideoNotFoundException(String id) {
        super(id.toString() + " is not found");
    }
}
