package com.kh.spring.pet;

import org.springframework.stereotype.Component;

@Component("cat")
public class Cat implements Pet {

	public Cat() {
		System.out.println("Cat 객체 생성!");
	}

	
}
