package io.webBack.ppmtool.payload;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonFormat(shape = JsonFormat.Shape.ANY)
//@JsonPropertyOrder({ "username", "password" })
//@JsonIgnoreProperties
public class LoginRequest {
	
	public LoginRequest() {
	
	}
	@NotBlank(message = "Username cannot be Blank")
	private String username;
	
	@NotBlank(message = "Password cannot be Blank")
	private String password;
	
    
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
