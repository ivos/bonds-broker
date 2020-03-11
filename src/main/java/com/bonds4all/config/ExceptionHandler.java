package com.bonds4all.config;

import com.bonds4all.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice("com.bonds4all")
public class ExceptionHandler {

	private ErrorResponse createErrorResponse(HttpServletRequest request, HttpStatus status) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setStatus(status.value());
		errorResponse.setError(status.getReasonPhrase());
		errorResponse.setPath(request.getRequestURI());
		return errorResponse;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ValidationException.class})
	@ResponseBody
	ResponseEntity<ErrorResponse> handle(HttpServletRequest request, ValidationException exception) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ErrorResponse errorResponse = createErrorResponse(request, status);
		errorResponse.setErrors(exception.getValidationErrors());
		return new ResponseEntity<>(errorResponse, status);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handle(HttpServletRequest request, EntityNotFoundException exception) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse errorResponse = createErrorResponse(request, status);
		errorResponse.setError("Not Found");
		return new ResponseEntity<>(errorResponse, status);
	}
}
