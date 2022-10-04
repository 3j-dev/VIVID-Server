package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserAccessDeniedException extends BusinessException {

    public UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
