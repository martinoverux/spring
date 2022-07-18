package com.kh.spring.demo.model.exception;

public class DevNotFoundException extends RuntimeException {

	public DevNotFoundException() {
		super();
	}

	public DevNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DevNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DevNotFoundException(String arg0) {
		super(arg0);
	}

	public DevNotFoundException(Throwable arg0) {
		super(arg0);
	}
}