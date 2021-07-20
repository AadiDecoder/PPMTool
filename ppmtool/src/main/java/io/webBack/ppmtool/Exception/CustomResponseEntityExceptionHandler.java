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

	
	 @ExceptionHandler(ProjectNotFoundsException.class)
	    public final ResponseEntity<Object> handleProjectNotFoundsssException(ProjectNotFoundsException ex, WebRequest request){
		 System.out.println(ex.getMessage());
	        ProjectNeverFoundExceptionResponse exceptionResponse = new ProjectNeverFoundExceptionResponse();
	       exceptionResponse.setProject_Not_Found(ex.getMessage());
	        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	    }
	 @ExceptionHandler
		public ResponseEntity<ProjectIdExceptionResponse> handleProjectIdException(ProjectIdException ex,WebRequest request){
		
			System.out.println("inside custom exception handler");
			ProjectIdExceptionResponse excpetionResp= new ProjectIdExceptionResponse(ex.getMessage());
			return new ResponseEntity<>( excpetionResp ,HttpStatus.BAD_REQUEST);
			
		   
		}
	 
	 @ExceptionHandler(ProjectTaskNotFoundException.class)
	 public final ResponseEntity<Object> handleProjectTaskNotFoundException(ProjectTaskNotFoundException ex, WebRequest req){
		 ProjectTaskNotFoundExceptionResponse response= new ProjectTaskNotFoundExceptionResponse();
		 response.setProjectTaskNotFound(ex.getMessage());
		 
		 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	 }
	 
	 @ExceptionHandler(userAlreadyExistsException.class)
	 public final ResponseEntity<Object> handleuserNameAlreadyExistsException(userAlreadyExistsException ex , WebRequest req){
		 userNameAlreadyExistsResponse res=new userNameAlreadyExistsResponse();
		 res.setUserAlreadyExists(ex.getMessage());
		 return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	 }
}
