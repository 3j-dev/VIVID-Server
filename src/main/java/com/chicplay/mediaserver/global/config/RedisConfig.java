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

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis-note.host}")
    private String noteRedisHost;

    @Value("${spring.redis-note.port}")
    private int noteRedisPort;

    @Value("${spring.redis-note.password}")
    private String noteRedisPassword;

    @Value("${spring.redis-user.host}")
    private String userRedisHost;

    @Value("${spring.redis-user.port}")
    private int userRedisPort;

    @Value("${spring.redis-user.password}")
    private String userRedisPassword;

    @Bean(name = "noteRedisConnectionFactory")
    @Primary
    public RedisConnectionFactory noteRedisConnectionFactory() {

        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(noteRedisHost);
        configuration.setPort(noteRedisPort);
        configuration.setPassword(noteRedisPassword);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean(name = "noteRedisTemplate")
    public RedisTemplate<?, ?> noteRedisTemplate(@Qualifier(value = "noteRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

//
//    @Bean
//    @Qualifier("noteRedisCacheManager")
//    public RedisCacheManager noteRedisCacheManager() {
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
//                .fromConnectionFactory(noteRedisConnectionFactory())
//                .cacheDefaults(redisCacheConfiguration)
//                .build();
//    }

    @Bean(name = "userRedisConnectionFactory")
    public RedisConnectionFactory userRedisConnectionFactory() {

        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(userRedisHost);
        configuration.setPort(userRedisPort);
        configuration.setPassword(userRedisPassword);

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


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModules(new JavaTimeModule(), new Jdk8Module());
        return mapper;
    }
}
