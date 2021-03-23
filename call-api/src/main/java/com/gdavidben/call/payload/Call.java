package com.gdavidben.call.payload;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdavidben.call.api.CallProperties;

@Schema(name = "Call", description = "Object that represents a call")
public class Call {

	private String id;

	@NotBlank(message = "Caller may not be blank")
	private String caller;

	@NotBlank(message = "Callee may not be blank")
	private String callee;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CallProperties.DATETIME_FORMAT)
	@NotNull(message = "Start may not be null")
	private LocalDateTime start;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CallProperties.DATETIME_FORMAT)
	@NotNull(message = "End may not be null")
	private LocalDateTime end;

	@NotNull(message = "Type may not be blank")
	private Type type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Call [id=" + id + ", caller=" + caller + ", callee=" + callee + ", start=" + start + ", end=" + end
				+ ", type=" + type + "]";
	}

}