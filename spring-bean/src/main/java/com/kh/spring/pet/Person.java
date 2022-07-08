package com.kh.spring.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Person {

	
	/**
	 * Pet type의 bean을 찾아서 의존 주입
	 *  - 필드에 주입
	 */
	@Autowired
	@Qualifier("cat")
	private Pet pet;

	public Person() {
		System.out.println("Person 객체 생성");
	}
	
//	@Autowired
//	@Qualifier("cat")  the annotation error
	public Person(Pet pet) {
		System.out.println("Person 객체 생성 & 의존 주입" + pet);
		this.pet = pet;
	}
 
	
	public Pet getPet() {
		return pet;
	}
	
//	@Autowired
//	@Qualifier("cat")
	public void setPet(Pet pet) {
		System.out.println("Pet 의존 주입 : " + pet);
		this.pet = pet;
	}
	
	@Override
	public String toString() {
		return "Person [  pet=" + pet + "]";
	}                  	
}