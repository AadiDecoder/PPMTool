package io.webBack.ppmtool.validator;

import java.util.Set;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.webBack.ppmtool.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user =(User) target;
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password Must be at least 6 Characters");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match","Password must Match");
		}
		
	}

}
