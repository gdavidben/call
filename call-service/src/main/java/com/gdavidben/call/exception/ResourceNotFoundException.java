package com.gdavidben.call.exception;

import java.io.Serializable;

public class ResourceNotFoundException extends Exception implements Serializable {

	private static final long serialVersionUID = 289670986822885998L;

	
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
