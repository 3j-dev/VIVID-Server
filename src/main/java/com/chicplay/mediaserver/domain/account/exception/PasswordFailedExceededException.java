package com.chicplay.mediaserver.domain.account.exception;

import com.chicplay.mediaserver.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordFailedExceededException extends RuntimeException{

    private ErrorCode errorCode;

    public PasswordFailedExceededException() {
        this.errorCode = ErrorCode.PASSWORD_FAILED_EXCEEDED;
    }
}
