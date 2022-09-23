package com.chicplay.mediaserver.global.error;


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

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(final SignatureException exception) {
        log.error("SignatureException", exception);
        final ErrorCode errorCode = ErrorCode.TOKEN_EXPIRED;
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(final ExpiredJwtException exception) {
        log.error("ExpiredJwtException", exception);
        final ErrorCode errorCode = ErrorCode.TOKEN_NOT_MATCHED;
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(final UnsupportedJwtException exception) {
        log.error("UnsupportedJwtException", exception);
        final ErrorCode errorCode = ErrorCode.TOKEN_NOT_SUPPORTED;
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(final IllegalStateException exception) {
        log.error("IllegalStateException", exception);
        final ErrorCode errorCode = ErrorCode.TOKEN_ILLEGAL_STATE;
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response,HttpStatus.valueOf(errorCode.getStatus()));
    }
}
