package com.gdavidben.call.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Refactor this class to make the methods more dynamics
public class HttpUtils {
	
	public static HttpClient createClient(Integer connectionTimeout) {
		return HttpClient.newBuilder()
						 .version(HttpClient.Version.HTTP_1_1)
						 .connectTimeout(Duration.ofSeconds(connectionTimeout))
						 .build();
	}

	public static Response get(HttpClient httpClient, String url, Integer readTimeout) {
		return request(httpClient, getRequest(url, null, readTimeout));
	}
	
	public static Response get(HttpClient httpClient, String url, Map<String, String> params, Integer readTimeout) {
		return request(httpClient, getRequest(url, params, readTimeout));
	}
	
	public static Response post(HttpClient httpClient, String url, Object body, Integer readTimeout) {
		return request(httpClient, postRequest(url, body, readTimeout));
	}
	
	public static Response delete(HttpClient httpClient, String url, String id, Integer readTimeout) {
		return request(httpClient, deleteRequest(url, id, readTimeout));
	}

	public static <T> T toObject(Object json, Class<T> clazz) { 
		return toObject(json, clazz, getObjectMapper());
	}
	
	public static <T> T toObject(Object json, Class<T> clazz, ObjectMapper objectMapper) {
		try {
			if(json instanceof InputStream) {
				return objectMapper.readValue((InputStream) json, clazz);
			} else {
				return objectMapper.readValue((String) json, clazz);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> toList(Object json, Class<T> clazz) {
		return toList(json, clazz, getObjectMapper());
	}
	
	public static <T> List<T> toList(Object json, Class<T> clazz, ObjectMapper objectMapper) {
		try {
			JavaType javaType = getObjectMapper().getTypeFactory().constructCollectionType(List.class, clazz);
			
			if(json instanceof InputStream) {
				return objectMapper.readValue((InputStream) json, javaType);
			} else {
				return objectMapper.readValue((String) json, javaType);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.findAndRegisterModules();
		
		return objectMapper;
	}
	
	private static Response request(HttpClient httpClient, HttpRequest httpRequest) {
		try {
			return HttpUtils.toResponse(send(httpClient, httpRequest));
		} catch (IOException | InterruptedException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	private static HttpRequest getRequest(String url, Map<String, String> params, Integer readTimeout) {
		return HttpRequest.newBuilder()
						  .timeout(Duration.ofSeconds(readTimeout))		  
						  .uri(URI.create((params != null) ? encodePath(url, params) : url))
						  .GET()
						  .build();
	}

	private static HttpRequest postRequest(String url, Object body, Integer readTimeout) {
		return HttpRequest.newBuilder()
						  .timeout(Duration.ofSeconds(readTimeout))		  
						  .header("Content-Type", "application/json;charset=UTF-8")
						  .uri(URI.create(url))
						  .POST(HttpRequest.BodyPublishers.ofString(toJson(body)))
						  .build();
	}

	private static HttpRequest deleteRequest(String url, String id, Integer readTimeout) {
		return HttpRequest.newBuilder()
						  .timeout(Duration.ofSeconds(readTimeout))
						  .uri(URI.create(url + (id != null ? "/" + id : "")))
						  .DELETE()
						  .build();
	}

	private static HttpResponse<?> send(HttpClient httpClient, HttpRequest httpRequest) throws IOException, InterruptedException {
		return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
	}
	
	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static String encodePath(String url, Map<String, String> params) {
		return params.keySet().stream()
							  .map(key -> key + "=" + encodeValue(params.get(key)))
							  .collect(Collectors.joining("&", url+"?", ""));
	}

	private static String toJson(Object object) {
		try {
			return getObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private static Response toResponse(HttpResponse<?> httpResponse) {
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		httpResponse.headers().map().entrySet().forEach(e -> headers.addAll(e.getKey(), e.getValue().toArray()));
		
		return Response.status(httpResponse.statusCode()).replaceAll(headers).entity(httpResponse.body()).build();
	}
}
