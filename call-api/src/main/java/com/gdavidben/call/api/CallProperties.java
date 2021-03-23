package com.gdavidben.call.api;

public class CallProperties {

	public static final String APPLICATION_TITLE = "Call API";
	public static final String APPLICATION_DESCRIPTION = "This API allows create, delete, get calls and get statistics about calls";
	public static final String APPLICATION_VERSION = "1.0.0";
	public static final String APPLICATION_SERVER_URL = "http://localhost:8080";
	public static final String APPLICATION_PATH = "/api";

	public static final String CALL_RESOURCE_PATH = "/call";

	public static final String TAG_CALL_NAME = "call";
	public static final String TAG_CALL_DESCRIPTION = "Call operations";
	
	public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	private CallProperties() {
		throw new IllegalStateException("Properties class");
	}
}