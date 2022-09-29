package com.chicplay.mediaserver;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableJpaAuditing
//@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10080)
@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
public class MediaServerApplication {



	public static void main(String[] args) {
		SpringApplication.run(MediaServerApplication.class, args);
	}

}
