package com.chicplay.mediaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
public class MediaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaServerApplication.class, args);
	}

}
