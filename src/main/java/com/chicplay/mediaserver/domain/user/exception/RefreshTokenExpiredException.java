package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.AccessDeniedException;
import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class RefreshTokenExpiredException extends AccessDeniedException {

    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
