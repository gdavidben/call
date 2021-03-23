package com.gdavidben.call.payload;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Validation", description = "Object that represents a validation error")
public class Validation {

	private String message;

	
	public Validation(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
