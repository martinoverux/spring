package com.kh.spring.tv;

public interface RemoteControl {
	
	public default void channelTo(int no) {
		System.out.println("채널을 " + no + "번으로 변경합니다.");
	}
	
}
	