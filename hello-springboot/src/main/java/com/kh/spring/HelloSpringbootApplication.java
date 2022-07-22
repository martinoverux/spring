package com.kh.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @Configuration
 * @EnableAutoConfiguration - 자동으로 context 구성
 * @ComponentScan - 현재패키지 하위의 @Component를 빈으로 등록
 *
 */
@SpringBootApplication
public class HelloSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringbootApplication.class, args);
	}

}
