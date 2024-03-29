package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import com.chicplay.mediaserver.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class PasswordFailedExceededException extends InvalidValueException {

    public PasswordFailedExceededException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
