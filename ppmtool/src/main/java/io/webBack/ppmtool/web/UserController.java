package io.webBack.ppmtool.web;

import java.util.HashMap;
import static io.webBack.ppmtool.security.SecurityConstant.*;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.webBack.ppmtool.Exception.userAlreadyExistsException;
import io.webBack.ppmtool.domain.User;
import io.webBack.ppmtool.payload.JWTLoginSuccessResponse;
import io.webBack.ppmtool.payload.LoginRequest;
import io.webBack.ppmtool.security.JwtTokenProvider;
import io.webBack.ppmtool.services.CustomUserDetailService;
import io.webBack.ppmtool.services.MapValidationErrorService;
import io.webBack.ppmtool.services.UserService;
import io.webBack.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest , BindingResult result) throws Exception
	{
		System.out.println("Inside login post method");
		String username=loginRequest.getUsername();
	    StringBuffer sbUser=new StringBuffer(username);
	    sbUser.delete(0, 2);
	    sbUser.reverse();
	    sbUser.delete(0,2);
	    sbUser.reverse();
	    
	    String password= loginRequest.getPassword();
	    StringBuffer sbPass=new StringBuffer(password);
	    sbPass.delete(0, 2);
	    sbPass.reverse();
	    sbPass.delete(0,2);
	    sbPass.reverse();
	    
	    
	    
	    System.out.println(sbUser+" adarsh");
		 Map<String , String> errorMap = mapValidationErrorService.MapValidationService(result);
		 if(!errorMap.isEmpty())
		 { 
		     return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		 }
		 
		 try {
//		 Authentication authentication = authManager.authenticate(
//				  new UsernamePasswordAuthenticationToken(loginRequest.getUsername() , loginRequest.getPassword())
//				 );
		 Authentication authentication = authManager.authenticate(
				  new UsernamePasswordAuthenticationToken(sbUser.toString() , sbPass.toString())
				 );
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 }
		 catch(Exception e) {
			 throw new Exception("Incorrect Username or password",e);
		 }
			
//		 final UserDetails userDetail=userDetailService.loadUserByUsername(loginRequest.getUsername());
		 final UserDetails userDetail=userDetailService.loadUserByUsername(sbUser.toString());
		 
		
		 String jwt = TOKEN_PREFIX + tokenProvider.generateToken(userDetail);
		 
		 System.out.println("ahha"+jwt);
		
		 return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user , BindingResult result){
		
		//validate password match
		userValidator.validate(user, result);
		try {
		 Map<String , String> errorMap = mapValidationErrorService.MapValidationService(result);
		 if(!errorMap.isEmpty())
		 { 
		     return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		 }
		 user.setConfirmPassword("");
		 User newUser = userService.saveUser(user);
	
		 return new ResponseEntity<User>(newUser , HttpStatus.CREATED);
		}
		catch(Exception e) {
			System.out.println("inside exception handling");
			throw new userAlreadyExistsException("User with this " + user.getUsername() +" already Exists");
		}
	}

}
