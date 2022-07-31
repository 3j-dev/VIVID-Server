package com.chicplay.mediaserver.domain.account.application;

import com.chicplay.mediaserver.domain.account.dao.AccountRepository;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpResponse;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.global.exception.ErrorCode;
import com.chicplay.mediaserver.test.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AccountServiceTest extends ServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("[service] signup 성공 테스트")
    public void signup_성공() throws Exception {

        //given
        String USER_EMAIL = "test@naver.com";
        String USER_NAME = "김철수";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

        given(accountRepository.save(any())).willReturn(accountSignUpRequest.toEntity());

        //when
        Account account = accountRepository.save(accountSignUpRequest.toEntity());

        //then
        assertThat(account).isNotNull();
        assertThat(account.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(account.getName()).isEqualTo(USER_NAME);
        assertThat(account.getPassword()).isNotEqualTo(USER_PASSWORD)
    }

    @Test
    @DisplayName("[service] signup 이메일 중복")
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