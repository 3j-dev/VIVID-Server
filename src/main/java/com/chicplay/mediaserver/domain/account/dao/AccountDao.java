package com.chicplay.mediaserver.domain.account.dao;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.QAccount;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.exception.AccountNotFoundException;
import com.chicplay.mediaserver.domain.individual_video.domain.QIndividualVideo;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.exception.VideoNotFoundException;
import com.chicplay.mediaserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountDao {

    private final JPAQueryFactory query;

    public Account findByEmail(final String email) {

        // fetch join + queryDLS를 통한 get
        Optional<Account> account = Optional.ofNullable(query.select(QAccount.account)
                .from(QAccount.account)
                .leftJoin(QAccount.account.videoSpaceParticipants, QVideoSpaceParticipant.videoSpaceParticipant)
                .fetchJoin()
                .where(QAccount.account.email.eq(email))
                .distinct().fetchOne());

        // not found exception
        account.orElseThrow(() -> new AccountNotFoundException(email));

        return account.get();
    }



}
