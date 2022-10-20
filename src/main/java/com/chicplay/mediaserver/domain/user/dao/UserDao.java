package com.chicplay.mediaserver.domain.user.dao;

import com.chicplay.mediaserver.domain.user.domain.QUser;
import com.chicplay.mediaserver.domain.user.domain.User;
import com.chicplay.mediaserver.domain.user.exception.UserNotFoundException;
import com.chicplay.mediaserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserDao {

    private final JPAQueryFactory query;

    public User findByEmail(final String email) {

        // fetch join + queryDLS를 통한 get
        Optional<User> user = Optional.ofNullable(query.select(QUser.user)
                .from(QUser.user)
                .leftJoin(QUser.user.videoSpaceParticipants, QVideoSpaceParticipant.videoSpaceParticipant)
                .fetchJoin()
                .where(QUser.user.email.eq(email))
                .distinct().fetchOne());

        // not found exception
        user.orElseThrow(() -> new UserNotFoundException(email));

        return user.get();
    }


}
