package com.chicplay.mediaserver.domain.account.domain;

import static org.junit.jupiter.api.Assertions.*;

public class AccountBuilder {

    public static String USER_EMAIL = "test@naver.com";
    public static String USER_NAME = "김철수";
    public static String USER_PASSWORD = "qwer1234";

    public static Account build(){
        Account account = Account.builder()
                .email(USER_EMAIL).name(USER_NAME)
                .password(Password.builder().password(USER_PASSWORD).build())
                .build();

        return account;
    }

    public static Account build(String email, String name, String pwd){

        Account account = Account.builder()
                .email(email).name(name)
                .password(Password.builder().password(pwd).build())
                .build();
        return account;
    }
}