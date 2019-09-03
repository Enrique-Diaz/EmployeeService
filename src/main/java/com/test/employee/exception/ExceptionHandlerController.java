package com.test.employee.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.test.employee.util.EmployeeConstant;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

	@Autowired
	private Logger logger;
	
	/*
	 * To handle checked exceptions and business exceptions
	 * */
	@ExceptionHandler(APIException.class)
	public ResponseEntity<?> apiException(APIException apiException){
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		mapResponse.put("statusMessage", apiException.getStatusMessage());
		mapResponse.put("statusCode", apiException.getStatusCode());
		
		logger.error("apiException: ", apiException);
		return new ResponseEntity<>(mapResponse, apiException.getHttpStatusCode());
	}
	
	/*
	 * to handle unchecked exceptions like;
	 * IndexOutOfBoundsException, IllegalArgumentException
	 * NumberFormatException, NullPointerException...
	 * */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> uncheckedException(Exception exception){
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		mapResponse.put("statusMessage", EmployeeConstant.UNEXPECTED_ERROR.getMessage());
		mapResponse.put("statusCode", EmployeeConstant.UNEXPECTED_ERROR.getCode());
		
		logger.error("uncheckedException", exception);
		return new ResponseEntity<>(mapResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}