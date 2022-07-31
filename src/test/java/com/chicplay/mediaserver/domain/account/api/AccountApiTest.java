package com.chicplay.mediaserver.domain.account.api;

import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
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

class AccountApiTest extends IntegrationTest {

    @Test
    @DisplayName("[api] signup 성공 테스트")
    public void signup_성공() throws Exception {

        //given
        String USER_EMAIL = "test@naver.com";
        String USER_NAME = "김철수";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountSignUpRequest)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(USER_EMAIL))
                .andExpect(jsonPath("name").value(USER_NAME))
                .andExpect(jsonPath("password").value(notNullValue()))
        ;
    }


    @Test
    @DisplayName("[api] signup 시, invalid email로 인한 실패 테스트")
    public void signup_이메일_유효하지않은_입력값() throws Exception {

        //given
        String USER_EMAIL = "asdasdwedasdcom";
        String USER_NAME = "홍길동";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

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
    @DisplayName("[api] signup 시, email 중복")
    public void signup_이메일_중복() throws Exception {

        //given
        String USER_EMAIL = "asdasd@naver.com";
        String USER_NAME = "홍길동";
        String USER_PASSWORD = "qwer1234";

        AccountSignUpRequest accountSignUpRequest = new AccountSignUpRequest(USER_EMAIL, USER_NAME, USER_PASSWORD);

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
        ;
    }
}