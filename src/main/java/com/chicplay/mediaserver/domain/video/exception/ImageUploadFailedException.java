package com.chicplay.mediaserver.domain.video.exception;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ImageUploadFailedException extends BusinessException {

    public ImageUploadFailedException() {
        super(ErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
