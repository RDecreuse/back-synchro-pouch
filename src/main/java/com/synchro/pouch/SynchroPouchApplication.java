package com.synchro.pouch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.synchro.pouch")
@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
public class SynchroPouchApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SynchroPouchApplication.class, args);
	}
}
