package com.gdavidben.call.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.gdavidben.call.payload.Type;

import io.quarkus.mongodb.panache.MongoEntity;

@MongoEntity(collection = "call")
public class CallEntity {

	private ObjectId id;
	private String caller;
	private String callee;
	private LocalDateTime start;
	private LocalDateTime end;
	private Type type;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}
	
	public LocalDate getLocalDateEnd() {
		return end.toLocalDate();
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
