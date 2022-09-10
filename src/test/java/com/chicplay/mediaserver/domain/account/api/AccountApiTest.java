package com.chicplay.mediaserver.domain.account.api;

import com.chicplay.mediaserver.domain.account.domain.AccountBuilder;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import com.chicplay.mediaserver.test.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountApiTest extends IntegrationTest {

    @Test
    @DisplayName("[AccountApi] signup 성공 테스트")
    public void signup_성공() throws Exception {

        //given
        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountSignUpRequest)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(AccountBuilder.USER_EMAIL))
                .andExpect(jsonPath("name").value(AccountBuilder.USER_NAME))
        ;
    }


    @Test
    @DisplayName("[AccountApi] signup 시, invalid email로 인한 실패 테스트")
    public void signup_이메일_유효하지않은_입력값() throws Exception {

        //given
        String USER_INVALID_EMAIL = "testnaver.com";
        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_INVALID_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountSignUpRequest)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("[AccountApi] signup 시, email 중복")
    public void signup_이메일_중복() throws Exception {

        //given
        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);

        //when
        // 첫번째 계정 등록 api
        mvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountSignUpRequest)));

        // 두번째 계정 등록 api
        ResultActions resultActions = mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountSignUpRequest)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorCode.EMAIL_DUPLICATION.getCode()))
        ;
    }

}