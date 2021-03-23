package com.gdavidben.call.utils;

import java.util.List;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.gdavidben.call.payload.Validation;

public class ResponseUtils {

	private ResponseUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static Response created(Object object) {
		return Response.status(Status.CREATED).entity(object).build();
	}
	
	public static Response ok(Object object, Integer cache) {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(cache);
		
		return Response.ok(object).cacheControl(cacheControl).build();
	}
	
	public static Response ok(Object object) {
		return Response.ok(object).build();
	}

	public static Response notFound() {
		return Response.status(Status.NOT_FOUND).build();
	}
	
	public static Response noContent() {
		return Response.status(Status.NO_CONTENT).build();
	}
	
	public static Response badRequest(String message) {
		return Response.status(Status.BAD_REQUEST).entity(new Validation(message)).build();
	}
	
	public static Response badRequest(List<Validation> validations) {
		return Response.status(Status.BAD_REQUEST).entity(validations).build();
	}
	
	public static Response serverError(String message) {
		return Response.serverError().entity(new Validation(message)).build();
	}
	
	public static Response notImlpemented() {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
}
