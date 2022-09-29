package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class HeaderAccessTokenInvalidException extends BusinessException {

    public HeaderAccessTokenInvalidException() {
        super(ErrorCode.HEADER_ACCESS_TOKEN_INVALID);
    }
}
