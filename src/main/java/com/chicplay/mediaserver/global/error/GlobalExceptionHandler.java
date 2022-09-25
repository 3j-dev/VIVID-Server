package com.chicplay.mediaserver.global.error;


import com.chicplay.mediaserver.domain.user.exception.RefreshTokenExpiredException;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenNotFoundException;
import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.EntityNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception) {
        log.error("handleBusinessException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException exception) {
        log.error("handleEntityNotFoundException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponse response = ErrorResponse.from(exception.getErrorCode());
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }


    @ExceptionHandler(RefreshTokenNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(final RefreshTokenNotFoundException exception) {
        log.error("RefreshTokenNotFoundException", exception);
        final ErrorCode errorCode = ErrorCode.REFRESH_TOKEN_NOT_FOUND;
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    protected ResponseEntity<ErrorResponse> handleRefreshTokenExpiredException(final RefreshTokenExpiredException exception) {
        log.error("RefreshTokenExpiredException", exception);
        final ErrorCode errorCode = ErrorCode.REFRESH_TOKEN_EXPIRED;
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }
}
