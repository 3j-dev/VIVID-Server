package com.chicplay.mediaserver.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisUserConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis-user.port}")
    private int userRedisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;


    //@SpringSessionRedisConnectionFactory
    @Bean(name = "userRedisConnectionFactory")
    public RedisConnectionFactory userRedisConnectionFactory() {

        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(userRedisPort);
        configuration.setPassword(redisPassword);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean(name = "userRedisTemplate")
    public RedisTemplate<?, ?> userRedisTemplate(@Qualifier(value = "userRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

//    @Bean(name = "userRedisCacheManager")
//    public RedisCacheManager userRedisCacheManager() {
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
//                .defaultCacheConfig()
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair
//                                .fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair
//                                .fromSerializer(new StringRedisSerializer())
//                )
//                .entryTtl(Duration.ofMillis(10L));
//
//        return RedisCacheManager.RedisCacheManagerBuilder
//                .fromConnectionFactory(userRedisConnectionFactory())
//                .cacheDefaults(redisCacheConfiguration)
//                .build();
//    }

}
