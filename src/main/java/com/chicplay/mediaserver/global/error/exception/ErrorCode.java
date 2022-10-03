package com.chicplay.mediaserver.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE("C01", "Invalid Input Value.",HttpStatus.BAD_REQUEST.value()),
    METHOD_NOT_ALLOWED("C02", "Invalid Input Value.",HttpStatus.METHOD_NOT_ALLOWED.value()),
    ENTITY_NOT_FOUND( "C03", "Entity Not Found.",HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR( "C04", "Server Error.",HttpStatus.INTERNAL_SERVER_ERROR.value()),
    INVALID_TYPE_VALUE("C05", " Invalid Type Value.",HttpStatus.BAD_REQUEST.value()),

    // account
    USER_ACCESS_DENIED( "A01", "Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    PASSWORD_FAILED_EXCEEDED("A02", "Password attempts exceeded.", HttpStatus.BAD_REQUEST.value()),
    EMAIL_DUPLICATION("A03", "Email is Duplication",HttpStatus.BAD_REQUEST.value()),


    // account - token
    ACCESS_TOKEN_EXPIRED("AT01", "Access Token is Expired",HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_INVALID("AT02", "Access Token is Invalid.",HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_NOT_FOUND("AT03", "Access Token is not Found in Header.",HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_NOT_FOUND("AT04", "Refreshed Token is Not Found.",HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_EXPIRED("AT05", "Refreshed Token is Expired.",HttpStatus.UNAUTHORIZED.value()),


    // video
    IMAGE_UPLOAD_FAILED("V02", "Image upload is failed.",HttpStatus.INTERNAL_SERVER_ERROR.value());

    private final String code;
    private final String message;
    private int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
