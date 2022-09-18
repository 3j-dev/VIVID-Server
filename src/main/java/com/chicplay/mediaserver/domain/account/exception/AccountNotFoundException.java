package com.chicplay.mediaserver.domain.account.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;

import java.util.UUID;

public class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException(UUID id) {
        super(id.toString() + " is not found");
    }

    public AccountNotFoundException(String emial) {
        super(emial + " is not found");
    }
}
