package com.chicplay.mediaserver.domain.video.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import com.chicplay.mediaserver.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class ImageUploadFailedException extends InvalidValueException {

    public ImageUploadFailedException() {
        super(ErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
