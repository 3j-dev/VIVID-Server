package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

import java.util.UUID;

public class RefreshTokenNotFoundException extends EntityNotFoundException {

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
