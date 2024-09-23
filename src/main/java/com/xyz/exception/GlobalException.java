package com.xyz.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(UserException ue, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(ue.getMessage());
		errorDetails.setMessage(request.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetails> messageExceptionHandler(MessageException me,WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(me.getMessage());
		errorDetails.setMessage(request.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetails> chatExceptionHandler(ChatException ce,WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		
		errorDetails.setError(ce.getMessage());
		errorDetails.setMessage(request.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException mae, WebRequest request){
		String error = mae.getBindingResult().getFieldError().getDefaultMessage();
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError("method argument not valid");
		errorDetails.setMessage(error);
		errorDetails.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundException(NoHandlerFoundException ne, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError("Endpoint Not Found");
		errorDetails.setMessage(ne.getMessage());
		errorDetails.setTimestamp(LocalDateTime.now());
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.NOT_FOUND);
		
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> otherExceptionHandeler(Exception e,WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(e.getMessage());
		errorDetails.setMessage(request.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}

}
