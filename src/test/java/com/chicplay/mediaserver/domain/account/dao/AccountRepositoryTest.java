package com.chicplay.mediaserver.domain.account.dao;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.Password;
import com.chicplay.mediaserver.test.RepositoryTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends RepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {

        //given
        String USER_EMAIL = "test@naver.com";
        String USER_NAME = "김철수";
        String USER_PASSWORD = "qwer1234";

        account = Account.builder()
                .email(USER_EMAIL).name(USER_NAME)
                .password(Password.builder().password(USER_PASSWORD).build())
                .build();

    }

    @Test
    @DisplayName("[account repository] save 성공 테스트")
    public void save_성공() {

        // when
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getName()).isEqualTo(account.getName());
        assertThat(savedAccount.getEmail()).isEqualTo(account.getEmail());
        assertThat(savedAccount.getPassword()).isNotNull();
        assertThat(savedAccount.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedAccount.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("[account repository] findByEmail 성공 테스트")
    public void findByEmail_성공() {

        // given
        Account savedAccount = accountRepository.save(account);

        // when
        Optional<Account> accountByEmail = accountRepository.findByEmail(account.getEmail());

        //then
        assertThat(savedAccount.getId()).isEqualTo(accountByEmail.get().getId());

    }

    @Test
    @DisplayName("[account repository] existsByEmail_존재하는경우_true")
    public void existsByEmail_존재하는경우_true() {

        // given
        Account savedAccount = accountRepository.save(account);

        // when
        final boolean existsByEmail = accountRepository.existsByEmail(account.getEmail());

        // then
        assertThat(existsByEmail).isTrue();
    }

    @Test
    @DisplayName("[account repository] existsByEmail_존재하는경우_false")
    public void existsByEmail_존재하지않은_경우_false() {

        // given
        Account savedAccount = accountRepository.save(account);

        // when
        final boolean existsByEmail = accountRepository.existsByEmail("thisissnotemail@lol.kr");

        // then
        assertThat(existsByEmail).isFalse();
    }

}