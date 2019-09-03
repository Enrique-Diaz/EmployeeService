package com.test.employee.exception;

import org.springframework.http.HttpStatus;

public class APIException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String statusMessage;
	private Integer statusCode;
	private HttpStatus httpStatusCode;

	/*
	 * This method sets a custom message as the RuntimeException message
	 * */
	public APIException(String statusMessage, Integer statusCode, HttpStatus httpStatusCode) {
		super(statusMessage);
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
		this.httpStatusCode = httpStatusCode;
	}
	
	/*
	 * This method sets a third party message as the RuntimeException message
	 * */
	public APIException(String message, String statusMessage, Integer statusCode, HttpStatus httpStatusCode) {
		super(message);
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
}