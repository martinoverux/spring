package com.kh.spring.builder.pattern;

import java.time.LocalDate;

public class PersonMain {

	public static void main(String[] args) {
		Person person1 = Person.builder()
								.username("honggd")
								.password("1234")
								.build();
		Person person2 = Person.builder()
							   .username("sinsa")
							   .password("1234") 
							   .birthday(LocalDate.of(1999, 9, 9))
							   .address("서울시 강서구")
							   .married(true)
							   .build();
			
		System.out.println(person1);
		System.out.println(person2);

	}

}
