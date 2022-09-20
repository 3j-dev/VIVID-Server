package com.chicplay.mediaserver.domain.user.api;

import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("/api/account")
    public UserSignUpResponse signUp(@RequestBody @Valid final UserLoginRequest userLoginRequest){

        UserSignUpResponse userSignUpResponse = userService.signUp(userLoginRequest);

        return userSignUpResponse;
    }

    @PostMapping("/api/test")
    public String test(){

        return "hello_test";
    }
}
