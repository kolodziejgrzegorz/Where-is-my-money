package com.whereIsMyMoney.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errors = new ArrayList<String>();
		
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, errorMessage, headers, errorMessage.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String error = ex.getParameterName() + " parameter is missing";

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}

	@ExceptionHandler({ DataNotFoundException.class })
	public ResponseEntity<Object> handleDataNotFound(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), "404");
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}

	@ExceptionHandler({ DataExistsException.class })
	public ResponseEntity<Object> handleDataExists(DataExistsException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, ex.getMessage(), "409");
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}
	
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "This is an invalid json. The request can not be parsed";
		String message = "This error can be cause by: wrong JSON format or wrong data format.";
		
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, message, error);
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	} 
  
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
				builder.toString());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
				builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleOthersExceptions(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
	}
}
