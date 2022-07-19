package com.kh.spring.builder.pattern;

import java.time.LocalDate;

public class UserMain {

	public static void main(String[] args) {

		User user1 = User.builder("honggd", "1234").build();
		User user2 = User.builder("sinsa", "1234")
						 .birthday(LocalDate.of(1999, 9, 9))
						 .address("서울시 강서구")
						 .married(true)
						 .build();
		
		System.out.println(user1);
		System.out.println(user2);
	}

}
