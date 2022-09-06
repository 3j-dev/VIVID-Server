package com.chicplay.mediaserver.domain.account.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordFailedExceededException extends BusinessException {

    public PasswordFailedExceededException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
