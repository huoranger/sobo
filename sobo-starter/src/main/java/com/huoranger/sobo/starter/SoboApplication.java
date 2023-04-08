package com.huoranger.sobo.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.huoranger.sobo")
@MapperScan(value = {"com.huoranger.sobo.infrastructure.dal.dao"})
public class SoboApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SoboApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SoboApplication.class);
	}
}
