package com.hcl.ask_buddy.answer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	@ResponseBody
	public ErrorResponse handleNotFound(ResourceNotFoundException idNotFound)
	{
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());	
		error.setMessage(idNotFound.getMessage());
		return error;
		
	}

}
