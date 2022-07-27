package com.chicplay.mediaserver.domain.account.dao;

import com.chicplay.mediaserver.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}
