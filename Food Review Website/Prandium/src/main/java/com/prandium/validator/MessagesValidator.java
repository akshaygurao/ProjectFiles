package com.prandium.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.prandium.pojo.Messages;

public class MessagesValidator implements Validator{
	
	public boolean supports(@SuppressWarnings("rawtypes") Class aClass) {
		return aClass.equals(Messages.class);
	}
	
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "error.invalid.messages", "Message Required");
		
	}


}
