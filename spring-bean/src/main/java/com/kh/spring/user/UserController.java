package com.kh.spring.user;

public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		System.out.println("UserController 객체 생성 & 의존 주입 : " + userService);
		this.userService = userService;
	}
}