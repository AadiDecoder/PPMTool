package io.webBack.ppmtool.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {
	
public HashMap MapValidationService(BindingResult result){
	Map<String,String> errorMap= new HashMap<>();
//	errorMap=null;
	System.out.println(result.getFieldError());
	System.out.println("outside of map validation");
	
	if(result.hasErrors()) {
		System.out.println("inside error map vali");
		
		for(FieldError error: result.getFieldErrors()) 
		{
			System.out.println(error.getField()+" "+error);
			errorMap.put(error.getField(), error.getDefaultMessage());
		}
		
	}
	System.out.println(errorMap);
	
//	
//	if(errorMap!=null)
//	{
//		return new ResponseEntity<Map<String,String>>( errorMap ,HttpStatus.BAD_REQUEST);
//	}
//	
	
//	if(errorMap.isEmpty()) {
//		return new ResponseEntity<Map<String,String>>( errorMap ,HttpStatus.BAD_REQUEST);
////		return null;
//	}
	return (HashMap) errorMap;
}
}
