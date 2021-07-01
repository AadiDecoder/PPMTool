package io.webBack.ppmtool.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class ProjectIdException extends RuntimeException {

	
	public ProjectIdException(String message) {
		super(message);
	}
}
