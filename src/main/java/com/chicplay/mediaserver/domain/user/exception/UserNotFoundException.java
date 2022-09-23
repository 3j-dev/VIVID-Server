package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(UUID id) {
        super(id.toString() + " is not found");
    }

    public UserNotFoundException(String email) {
        super(email + " is not found");
    }
}
