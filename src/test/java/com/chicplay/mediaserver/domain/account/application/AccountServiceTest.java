package com.chicplay.mediaserver.domain.account.application;

import com.chicplay.mediaserver.domain.account.dao.AccountRepository;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpResponse;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.global.exception.ErrorCode;
import com.chicplay.mediaserver.test.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


class AccountServiceTest extends ServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void signUp_이메일_중복() {

        //given
        String USER_EMAIL = "qweqwe@naver.com";
        String USER_NAME = "홍길동";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

        given(accountRepository.existsByEmail(any())).willReturn(true);

        //when
        EmailDuplicateException exception = Assertions.assertThrows(EmailDuplicateException.class, () ->{
            accountService.signUp(accountSignUpRequest);
        });


        //then
        assertEquals(ErrorCode.EMAIL_DUPLICATION,exception.getErrorCode());
    }


}