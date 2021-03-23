package com.gdavidben.call.client;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.gdavidben.call.api.CallProperties;
import com.gdavidben.call.api.CallResource;
import com.gdavidben.call.client.utils.HttpUtils;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Type;

/**
 * 
 * You can use the Microprofile REST Client in order to interact with REST APIs with very little effort.
 * See more in: https://quarkus.io/guides/rest-client
 * 
 * In this challenge, I wanted to implement the client to show code :)
 * 
 * */
public class CallClient implements CallResource {

	private HttpClient httpClient;
	private String url;
	private Integer readTimeout;

	
	public CallClient(HttpClient httpClient, String url, Integer readTimeout) {
		super();
		this.httpClient = httpClient;
		this.url = url;
		this.readTimeout = readTimeout;
	}

	@Override
	public Response createCall(Call call) {
		return HttpUtils.post(httpClient, url, call, readTimeout);
	}

	@Override
	public Response createCalls(List<Call> calls) {
		return HttpUtils.post(httpClient, url + "/bulk", calls, readTimeout);
	}

	@Override
	public Response deleteCall(String id) {
		return HttpUtils.delete(httpClient, url, id, readTimeout);
	}

	@Override
	public Response findCall(String id) {
		
		if(id == null) {
			throw new RuntimeException("Cannot call findCall(id) with id null");
		}
		
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		
		return HttpUtils.get(httpClient, url, params, readTimeout);
	}

	@Override
	public Response listCalls(Type type, Integer offset, Integer limit) {
		Map<String, String> params = new HashMap<>();
		
		if(type != null) {
			params.put("type", type.toString());
		}
		if(offset != null) {
			params.put("offset", offset.toString());
		}
		if(limit != null) {
			params.put("limit", limit.toString());
		}
		
		return HttpUtils.get(httpClient, url, params, readTimeout);
	}

	@Override
	public Response statisticsCall() {
		return HttpUtils.get(httpClient, url + "/statistics", readTimeout);
	}

	public static class Builder {

		private String url;
		private Integer connectiontimeout;
		private Integer readTimeout;

		public Builder withUrl(String url) {
			if (url == null) {
				throw new IllegalArgumentException("Cannot call withUrl(url) with null");
			}

			this.url = url + CallProperties.APPLICATION_PATH + CallProperties.CALL_RESOURCE_PATH;
			
			return this;
		}

		public Builder withConnectiontimeout(Integer connectiontimeout) {
			this.connectiontimeout = connectiontimeout;
			return this;
		}

		public Builder withReadTimeout(Integer readTimeout) {
			this.readTimeout = readTimeout;
			return this;
		}

		public CallClient build() {
			if(this.url == null) {
				withUrl(CallProperties.APPLICATION_SERVER_URL);
			}
			
			if(this.readTimeout == null) {
				withReadTimeout(60);
			}
			
			if(this.connectiontimeout == null) {
				withConnectiontimeout(10);
			}
			
			HttpClient httpClient = HttpUtils.createClient(connectiontimeout);
			
			return new CallClient(httpClient, url, readTimeout);
		}
	}

}
