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
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@Transactional
public class UserAuthTokenDao {

    private final  RedisTemplate<String, Object> redisTemplate;

    public UserAuthTokenDao(@Qualifier("userRedisTemplate") RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = (RedisTemplate<String, Object>) redisTemplate;
    }

    // save refresh token redis
    public void saveRefreshToken(String userIp, String refreshToken) {

        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(userIp, refreshToken, 14, TimeUnit.DAYS);

    }

    // get refresh token redis
    public String getRefreshToken(String userIp) {

        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        // refresh token get from redis
        Optional<Object> refreshToken = Optional.ofNullable(stringStringValueOperations.get(userIp));

        // handle not found exception
        refreshToken.orElseThrow(() -> new RefreshTokenNotFoundException());

        return (String)refreshToken.get();

    }

    // remove refresh token
    public void removeRefreshToken(String userIp) {

        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(userIp, "", 1, TimeUnit.MILLISECONDS);
    }


}
