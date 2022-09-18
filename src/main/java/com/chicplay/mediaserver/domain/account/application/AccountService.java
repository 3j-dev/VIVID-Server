package com.chicplay.mediaserver.domain.account.application;

import com.chicplay.mediaserver.domain.account.dao.AccountDao;
import com.chicplay.mediaserver.domain.account.dao.AccountRepository;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpResponse;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceParticipantService;
import com.chicplay.mediaserver.domain.video_space.dao.VideoSpaceRepository;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.domain.video_space.dto.VideoSpaceParticipantSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountDao accountDao;

    public Account findByEmail(String email) {

        Account account = accountDao.findByEmail(email);

        return account;
    }

    public AccountSignUpResponse signUp(final AccountSignUpRequest dto) {

        // 이메일 중복 검사
        if (accountRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException();

        // save account
        Account account = dto.toEntity();

        // save 개인 영상 스페이스
        VideoSpace videoSpace = VideoSpace.builder().name("개인 영상").description(account.getName() + "님의 개인 영상 그룹 입니다.").build();

        // 개인 영상 스페이스 - account 매핑 테이블 생성
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().account(account).videoSpace(videoSpace).build();
        //VideoSpaceParticipant savedVideoSpaceParticipant = videoSpaceParticipantService.saveAfterAccountSaved(videoSpaceParticipant);
        Account savedAccount = accountRepository.save(account);


        return AccountSignUpResponse.builder().account(savedAccount).build();
    }
}
