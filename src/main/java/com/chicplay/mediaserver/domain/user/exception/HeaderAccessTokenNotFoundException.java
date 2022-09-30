package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class HeaderAccessTokenNotFoundException extends BusinessException {

    public HeaderAccessTokenNotFoundException() {
        super(ErrorCode.HEADER_ACCESS_TOKEN_INVALID);
    }
}
