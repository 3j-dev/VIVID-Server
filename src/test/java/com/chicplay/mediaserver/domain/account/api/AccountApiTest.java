package com.chicplay.mediaserver.domain.account.api;

import com.chicplay.mediaserver.domain.account.dto.AccountDto;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.test.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountApiTest extends IntegrationTest {

    @Test
    public void 회원가입_성공() throws Exception {

        //given
        String USER_EMAIL = "test@naver.com";
        String USER_NAME = "김철수";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountSignUpRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(notNullValue()))
                .andExpect(jsonPath("name").value(notNullValue()))
        ;
    }


    @Test
    public void 회원가입_유효하지않은_입력값() throws Exception {
        //given
        String USER_EMAIL = "asdasdqwedasd.com";
        String USER_NAME = "김철수";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountSignUpRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
        ;
    }
}