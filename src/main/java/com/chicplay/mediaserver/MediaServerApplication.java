package com.chicplay.mediaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class MediaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaServerApplication.class, args);
	}

}
