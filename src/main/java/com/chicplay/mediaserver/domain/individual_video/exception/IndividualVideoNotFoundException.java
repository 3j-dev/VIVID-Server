package com.chicplay.mediaserver.domain.individual_video.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

import java.util.UUID;

public class IndividualVideoNotFoundException extends EntityNotFoundException {

    public IndividualVideoNotFoundException(UUID id) {
        super(id.toString() + " is not found");
    }
}
