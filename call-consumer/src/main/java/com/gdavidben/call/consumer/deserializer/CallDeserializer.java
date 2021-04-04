package com.gdavidben.call.consumer.deserializer;

import com.gdavidben.call.payload.Call;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CallDeserializer extends ObjectMapperDeserializer<Call> {

	public CallDeserializer() {
		super(Call.class);
	}
}