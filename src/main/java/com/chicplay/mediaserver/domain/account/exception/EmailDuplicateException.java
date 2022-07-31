package com.chicplay.mediaserver.domain.account.exception;

import com.chicplay.mediaserver.global.exception.BusinessException;
import com.chicplay.mediaserver.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
