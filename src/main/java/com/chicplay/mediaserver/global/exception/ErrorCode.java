package com.chicplay.mediaserver.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {


    //account
    PASSWORD_FAILED_EXCEEDED("A_01", "비밀번호 실패 횟수가 초과했습니다.", 400),
    EMAIL_DUPLICATION("A_02", "이메일이 중복됐습니다.",400);

    private final String code;
    private final String message;
    private int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
