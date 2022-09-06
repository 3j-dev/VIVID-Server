package com.chicplay.mediaserver.domain.account.application;

import com.chicplay.mediaserver.domain.account.dao.AccountRepository;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.AccountBuilder;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import com.chicplay.mediaserver.test.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class AccountServiceTest extends ServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("[AccountService] signup 성공 테스트")
    public void signup_성공() throws Exception {

        //given
        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);

        given(accountRepository.save(any())).willReturn(accountSignUpRequest.toEntity());

        //when
        Account account = accountRepository.save(accountSignUpRequest.toEntity());

        //then
        assertThat(account).isNotNull();
        assertThat(account.getEmail()).isEqualTo(AccountBuilder.USER_EMAIL);
        assertThat(account.getName()).isEqualTo(AccountBuilder.USER_NAME);
        assertThat(account.getPassword()).isNotEqualTo(AccountBuilder.USER_PASSWORD);
    }

    @Test
    @DisplayName("[AccountService] signup 이메일 중복")
    public void signUp_이메일_중복() {

        //given
        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);
        given(accountRepository.existsByEmail(any())).willReturn(true);

        //when
        EmailDuplicateException exception = Assertions.assertThrows(EmailDuplicateException.class, () ->{
            accountService.signUp(accountSignUpRequest);
        });

        //then
        assertEquals(ErrorCode.EMAIL_DUPLICATION,exception.getErrorCode());
    }
}