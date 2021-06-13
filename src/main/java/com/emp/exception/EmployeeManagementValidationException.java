package com.emp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeManagementValidationException extends RuntimeException {

	private static final long serialVersionUID = -8524367284364078308L;

	private String errorCode;

	public EmployeeManagementValidationException(String message) {
		super(message);
	}

	public EmployeeManagementValidationException(String message, Throwable t) {
		super(message, t);
	}

	public EmployeeManagementValidationException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

}
