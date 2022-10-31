package com.chicplay.mediaserver.domain.individual_video.exception;

import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;

public class TextMemoStateNotExistException extends EntityNotFoundException {

    public TextMemoStateNotExistException() {
        super(ErrorCode.TEXT_MEMO_STATE_NOT_EXIST);
    }
}
