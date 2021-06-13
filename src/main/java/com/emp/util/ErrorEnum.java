package com.emp.util;

public enum ErrorEnum {

	EMPLOYEE_NOT_FOUND(1, "Employee not found for the given email id");

	private final Integer code;
	private final String message;

	private ErrorEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public int getCode() {
		return code;
	}

}
