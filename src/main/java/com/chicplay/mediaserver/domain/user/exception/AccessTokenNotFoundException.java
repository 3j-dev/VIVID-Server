package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.AccessDeniedException;
import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class AccessTokenNotFoundException extends AccessDeniedException {

    public AccessTokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
