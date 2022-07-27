package com.chicplay.mediaserver.domain.account.exception;

import com.chicplay.mediaserver.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException{

    private ErrorCode errorCode;

    public EmailDuplicateException() {
        this.errorCode = ErrorCode.EMAIL_DUPLICATION;
    }
}
