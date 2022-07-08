package com.kh.spring.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean 설정이 가능한 클래스
 * @Bean 메소드. 리턴값이 Spring Container가 관리할 빈 객체
 */
@Configuration
public class UserConfig {

	@Bean
	public UserController userController() {
		return new UserController(userService());
	}
	
	@Bean
	public UserService userService() {
		return new UserService();
	}
}
