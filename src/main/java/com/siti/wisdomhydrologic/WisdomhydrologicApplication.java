package com.siti.wisdomhydrologic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling//定时
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.siti.wisdomhydrologic.*.mapper")
@ServletComponentScan
public class WisdomhydrologicApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(WisdomhydrologicApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WisdomhydrologicApplication.class);
	}
}
