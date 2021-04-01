package com.gdavidben.call.interceptor;

import java.time.LocalDateTime;

public class LogTimeDetail {

	String method;

	Long duration;

	LocalDateTime dateTime;

	public LogTimeDetail(String method, Long duration, LocalDateTime dateTime) {
		super();
		this.method = method;
		this.duration = duration;
		this.dateTime = dateTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "LogTimeDetail [method=" + method + ", duration=" + duration + ", dateTime=" + dateTime + "]";
	}

}
