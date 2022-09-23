package com.chicplay.mediaserver.domain.user.dao;

import com.chicplay.mediaserver.domain.user.exception.RefreshTokenNotFoundException;
import jdk.dynalink.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Slf4j
@Transactional
public class UserAuthTokenDao {

    private final  RedisTemplate<String, Object> redisTemplate;

    public UserAuthTokenDao(@Qualifier("userRedisTemplate") RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = (RedisTemplate<String, Object>) redisTemplate;
    }

    // refresh token redis save
    public void saveRefreshToken(String email, String refreshToken) {

        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(email, refreshToken);

    }


    // refresh token redis get
    public String getRefreshToken(String email) {

        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        Optional<Object> accessToken = Optional.ofNullable(stringStringValueOperations.get(email));

        accessToken.orElseThrow(() -> new RefreshTokenNotFoundException());

        return (String)accessToken.get();

    }


}
