package com.prandium.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.prandium.pojo.Restaurant;


public class RestaurantValidator implements Validator {
	
	public boolean supports(@SuppressWarnings("rawtypes") Class aClass) {
		return aClass.equals(Restaurant.class);
	}
	
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "restaurantName", "error.invalid.restaurant", "Restaurant Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "restaurantLocation", "error.invalid.restaurant", "Restaurant Location Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "restaurantPhone", "error.invalid.restaurant", "Restaurant Phone Required");
		
	}

}
