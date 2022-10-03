package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class AccessTokenInvalidException extends BusinessException {

    public AccessTokenInvalidException() {
        super(ErrorCode.ACCESS_TOKEN_INVALID);
    }
}