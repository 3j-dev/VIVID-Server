package com.chicplay.mediaserver.domain.user.exception;

import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import com.chicplay.mediaserver.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends InvalidValueException {

    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
