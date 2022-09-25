package com.chicplay.mediaserver.domain.user.api;

import com.chicplay.mediaserver.domain.user.application.OAuthUserService;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.domain.user.dto.UserNewTokenReqeust;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    private final OAuthUserService oAuthUserService;

    @PostMapping("/api/test/account")
    public UserSignUpResponse signUp(@RequestBody @Valid final UserLoginRequest userLoginRequest){

        UserSignUpResponse userSignUpResponse = userService.signUp(userLoginRequest);

        return userSignUpResponse;
    }

    @PostMapping("/api/auth/token")
    public UserNewTokenReqeust issueNewAccessToken(HttpServletRequest request){

        UserNewTokenReqeust newAccessToken = oAuthUserService.getNewAccessTokenFromEmail(request.getHeader("Authorization"));


        return newAccessToken;
    }


    @PostMapping("/api/test")
    public String test(){

        return "hello_test";
    }
}
