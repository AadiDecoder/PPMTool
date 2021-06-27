package io.webBack.ppmtool.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ProjectIdExceptionResponse> handleProjectIdException(ProjectIdException ex,WebRequest request){
	
		System.out.println("inside custom exception handler");
		ProjectIdExceptionResponse excpetionResp= new ProjectIdExceptionResponse(ex.getMessage());
		return new ResponseEntity<>( excpetionResp ,HttpStatus.BAD_REQUEST);
	}
}
