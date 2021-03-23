package com.gdavidben.call.mapper;

import javax.inject.Singleton;

import org.bson.types.ObjectId;

import com.gdavidben.call.entity.CallEntity;
import com.gdavidben.call.payload.Call;

@Singleton
public class CallMapper {

	public ObjectId toObjectId(String id) {
		return new ObjectId(id);
	}

	public Call toCall(CallEntity callEntity) {
		Call call = new Call();

		call.setId(callEntity.getId().toString());
		call.setCaller(callEntity.getCaller());
		call.setCallee(callEntity.getCallee());
		call.setStart(callEntity.getStart());
		call.setEnd(callEntity.getEnd());
		call.setType(callEntity.getType());
		
		return call;
	}

	public CallEntity toCallEntity(Call call) {
		CallEntity callEntity = new CallEntity();
		
		callEntity.setCaller(call.getCaller());
		callEntity.setCallee(call.getCallee());
		callEntity.setStart(call.getStart());
		callEntity.setEnd(call.getEnd());
		callEntity.setType(call.getType());
		
		return callEntity;
	}
}
