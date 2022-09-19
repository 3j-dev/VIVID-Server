package com.chicplay.mediaserver.domain.user.application;

import com.chicplay.mediaserver.domain.user.dao.UserDao;
import com.chicplay.mediaserver.domain.user.dao.UserRepository;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpRequest;
import com.chicplay.mediaserver.domain.user.dto.UserSignUpResponse;
import com.chicplay.mediaserver.domain.user.exception.EmailDuplicateException;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    public User findByEmail() {

        String testEmail = "jsb100800@naver.com";

        User user = userDao.findByEmail(testEmail);

        return user;
    }

    public UserSignUpResponse signUp(final UserSignUpRequest dto) {

        // 이메일 중복 검사
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException();

        // save account
        User user = dto.toEntity();

        // save 개인 영상 스페이스
        VideoSpace videoSpace = VideoSpace.builder().name("개인 영상").description(user.getName() + "님의 개인 영상 그룹 입니다.").build();

        // 개인 영상 스페이스 - account 매핑 테이블 생성
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().user(user).videoSpace(videoSpace).build();
        //VideoSpaceParticipant savedVideoSpaceParticipant = videoSpaceParticipantService.saveAfterAccountSaved(videoSpaceParticipant);
        User savedUser = userRepository.save(user);


        return UserSignUpResponse.builder().user(savedUser).build();
    }
}
