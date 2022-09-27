package com.chicplay.mediaserver.domain.user.api;

import com.chicplay.mediaserver.domain.user.application.OAuthUserService;
import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.domain.user.dto.UserNewTokenReqeust;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

//    @PostMapping("/api/test/account")
//    public UserSignUpResponse signUp(@RequestBody @Valid final UserLoginRequest userLoginRequest){
//
//        UserSignUpResponse userSignUpResponse = userService.signUp(userLoginRequest);
//
//        return userSignUpResponse;
//    }

    @Operation(summary = "user logout api", description = "user가 logout 할 시 호출하는 api입니다. redis의 refresh token을 삭제합니다.")
    @PostMapping("/auth/logout")
    public void logout(HttpServletRequest request){

        oAuthUserService.removeRefreshTokenByLogout(request);
    }

    // access token 만료시 refresh token을 통해 재발급
    @Operation(summary = "user access token re-issue api", description = "redis의 refresh token을 활용하여 access token을 재발급합니다.")
    @GetMapping("/auth/token")
    public UserNewTokenReqeust issueNewAccessToken(HttpServletRequest request){

        UserNewTokenReqeust newAccessToken = oAuthUserService.getNewAccessToken(request);

        return newAccessToken;
    }


    @Operation(summary = "test api", description = "서버 연결을 테스팅 하기 위한 api 입니다.")
    @GetMapping("/api/test")
    public String test(){

        return "hello_test";
    }
}
