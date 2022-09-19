package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserRepository;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.domain.AccountBuilder;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpRequest;
import com.chicplay.mediaserver.domain.user.exception.EmailDuplicateException;
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


public class UserServiceTest extends ServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("[AccountService] signup 성공 테스트")
    public void signup_성공() throws Exception {

        //given
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);

        given(userRepository.save(any())).willReturn(userSignUpRequest.toEntity());

        //when
        User user = userRepository.save(userSignUpRequest.toEntity());

        //then
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(AccountBuilder.USER_EMAIL);
        assertThat(user.getName()).isEqualTo(AccountBuilder.USER_NAME);
        assertThat(user.getPassword()).isNotEqualTo(AccountBuilder.USER_PASSWORD);
    }

    @Test
    @DisplayName("[AccountService] signup 이메일 중복")
    public void signUp_이메일_중복() {

        //given
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME, AccountBuilder.USER_PASSWORD);
        given(userRepository.existsByEmail(any())).willReturn(true);

        //when
        EmailDuplicateException exception = Assertions.assertThrows(EmailDuplicateException.class, () ->{
            userService.signUp(userSignUpRequest);
        });

        //then
        assertEquals(ErrorCode.EMAIL_DUPLICATION,exception.getErrorCode());
    }
}