package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.user.dao.UserDao;
import com.chicplay.mediaserver.domain.user.dao.UserRepository;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpResponse;
import com.chicplay.mediaserver.domain.user.exception.AccessTokenNotFoundException;
import com.chicplay.mediaserver.domain.user.exception.EmailDuplicateException;
import com.chicplay.mediaserver.domain.user.exception.UserAccessDeniedException;
import com.chicplay.mediaserver.domain.user.exception.UserNotFoundException;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.global.auth.application.JwtProviderService;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    private final JwtProviderService jwtProviderService;

    // access token으로부터 현재 로그인된 user를 get한다.
    public User findByAccessToken() {

        String email = getEmailFromAuthentication();

        User user = userDao.findByEmail(email);

        return user;
    }

    // header access token에서 email 정보를 get해온다.
    public String getEmailFromAuthentication() {

        String email = jwtProviderService.getEmailFromHeaderAccessToken();

        return email;
    }

    // email을 통해 user find
    public User findByEmail(String email) {

        User user = userDao.findByEmail(email);

        return user;
    }

    // email로 유저가 존재하는지 검색
    public boolean existsByEmail(String email) {

        boolean existsByEmail = userRepository.existsByEmail(email);

        return existsByEmail;
    }

    public String getWebexAccessToken() {

        // find user
        User user = findByAccessToken();

        // access token get
        String webexAccessToken = user.getInstitution().getWebexAccessToken();

        // access token not found exception
        if (!StringUtils.hasText(webexAccessToken)) {
            throw new AccessTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_HEADER);
        }

        return webexAccessToken;
    }

    public UserSignUpResponse signUp(final UserLoginRequest dto) {

        // 이메일 중복 검사
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException();

        // save account
        User user = dto.toEntity();

        // save 개인 영상 스페이스
        VideoSpace videoSpace = VideoSpace.builder()
                .name("개인 영상")
                .description(user.getName() + "님의 개인 영상 그룹 입니다.")
                .hostEmail(user.getEmail())
                .build();

        // 개인 영상 스페이스 - account 매핑 테이블 생성
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().user(user).videoSpace(videoSpace).build();
        //VideoSpaceParticipant savedVideoSpaceParticipant = videoSpaceParticipantService.saveAfterAccountSaved(videoSpaceParticipant);

        User savedUser = userRepository.save(user);

        return UserSignUpResponse.builder().user(savedUser).build();
    }

    public void changeLastAccessIndividualVideoId(UUID lastAccessIndividualVideoId) {

        User user = findByAccessToken();

        // chagne last
        user.changeLastAccessIndividualVideoId(lastAccessIndividualVideoId);
    }

    // 접근된 email과 로그인한 email을 비교하여 유효한 접근인 체크하는 메소드
    public void checkValidUserAccess(String accessedEmail) {
        String email = getEmailFromAuthentication();
        if (!accessedEmail.equals(email))
            throw new UserAccessDeniedException();
    }

    // user의 ip를 get하는 메소드
    public String getUserIp() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userIp = request.getHeader("X-FORWARDED-FOR");

        if (userIp == null)
            userIp = request.getRemoteAddr();

        return userIp;
    }

}
