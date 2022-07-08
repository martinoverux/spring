package com.kh.spring.pet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PetMain {

	public static void main(String[] args) {
		 String configLocation = "pet-context.xml";
		 ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		 System.out.println("===================== Spring Container 초기화 완료 ======================");
		 
		 Person honggd = context.getBean(Person.class);
		 System.out.println(honggd);
		 System.out.println(honggd.hashCode());
		 
		 Person sinsa = context.getBean(Person.class);
		 System.out.println(sinsa);
		 System.out.println(sinsa.hashCode());
	}

}
