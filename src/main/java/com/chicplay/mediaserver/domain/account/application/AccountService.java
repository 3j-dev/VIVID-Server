package com.chicplay.mediaserver.domain.account.application;

import com.chicplay.mediaserver.domain.account.dao.AccountRepository;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account signUp(final AccountSignUpRequest dto){

        // 이메일 중복 검사
        if(accountRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException();

        return accountRepository.save(dto.toEntity());
    }
}
