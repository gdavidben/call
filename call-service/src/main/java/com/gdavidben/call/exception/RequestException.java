package com.gdavidben.call.exception;

import java.util.List;

import com.gdavidben.call.payload.Validation;

public class RequestException extends Exception {

	private static final long serialVersionUID = 1793547983511404176L;

	private List<Validation> validations;

	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestException(String message) {
		super(message);
	}
	
	public RequestException(String message, List<Validation> validations) {
		super(message);
		this.validations = validations;
	}
	
	public RequestException(List<Validation> validations) {
		super();
		this.validations = validations;
	}

	public List<Validation> getValidations() {
		return validations;
	}

	public void setValidations(List<Validation> validations) {
		this.validations = validations;
	}
}
