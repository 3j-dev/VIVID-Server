package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class AccessTokenNotFoundException extends BusinessException {

    public AccessTokenNotFoundException() {
        super(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
    }
}
