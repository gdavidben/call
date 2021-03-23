package com.gdavidben.call.mapper;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import com.gdavidben.call.payload.Validation;

@Singleton
public class ValidationMapper {

	public static Validation toValidation(ConstraintViolation<?> constraintViolation) {
		return new Validation(constraintViolation.getMessage());
	}

}
