package com.gdavidben.call.consumer;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.gdavidben.call.client.CallClient;
import com.gdavidben.call.payload.Call;

@ApplicationScoped
public class CallConsumer {

	private CallClient callclient = new CallClient.Builder().build();

	@Incoming("incalls")
	public Call consumerCalls(Call call) {
		callclient.createCall(call);
		System.out.println(call);
		
		return call;
	}

}
