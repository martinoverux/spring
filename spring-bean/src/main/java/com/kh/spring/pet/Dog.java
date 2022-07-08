package com.kh.spring.pet;

import org.springframework.stereotype.Component;

@Component
public class Dog implements Pet {

	public Dog() {
		System.out.println("Dog 객체 생성!");
	}

	
}
