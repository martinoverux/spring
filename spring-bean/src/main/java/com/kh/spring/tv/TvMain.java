package com.kh.spring.tv;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TvMain {

	public static void main(String[] args) {
		String configLocation = "application-context.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
		System.out.println("=================== Spring Container 초기화 완료 ===================");
		
		Tv lgTv1 = (Tv) applicationContext.getBean("lgTv");
		System.out.println(lgTv1);
		Tv lgTv2 = applicationContext.getBean(LgTv.class); // 형변환 불필요
		System.out.println(lgTv2);
		System.out.println(lgTv1 == lgTv2); // true
		
		// 리모콘 테스트
		lgTv1.channelTo(3);
		
		
		Tv samsungTv = applicationContext.getBean("samsungTv", SamsungTv.class);
		System.out.println(samsungTv);

	}

}
