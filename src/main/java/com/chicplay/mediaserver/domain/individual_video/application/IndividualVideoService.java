package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import com.chicplay.mediaserver.domain.individual_video.dao.TextMemoStateRedisRepository;
import com.chicplay.mediaserver.domain.individual_video.domain.TextMemoState;
import com.chicplay.mediaserver.domain.individual_video.dto.TextMemoStateSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class IndividualVideoService {

    private final TextMemoStateRedisRepository textMemoStateRedisRepository;

    @Transactional
    public TextMemoState saveTextMemoState (final TextMemoStateSaveRequest dto){

        return textMemoStateRedisRepository.save(dto.toEntity());
    }


}
