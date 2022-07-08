package com.kh.spring.tv;

/**
 * 
 * 의존객체 주입 Dependency Injection
 * 1. field
 * 2. setter
 * 3. constructor
 *
 */
public class LgTv implements Tv {
	

	private RemoteControl remoteControl;
	
	public LgTv() {
		System.out.println("LgTv 객체 생성!");
	}
	
	public void setRemoteControl(RemoteControl remoteControl) {
		System.out.println("LgTv 의존 주입! : " + remoteControl );
		this.remoteControl = remoteControl;
	}
	
	public void channelTo(int no) {
		remoteControl.channelTo(no);
	}

	@Override
	public void powerOn() {
		System.out.println("LgTv 전원을 켭니다.");
	}

}
