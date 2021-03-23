package com.gdavidben.call.payload;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CallIdStatistic", description = "Object that represents a statistic about a CallID")
public class CallIdStatistic {

	private String callID;

	private Long quantity;

	
	public CallIdStatistic() {
		super();
	}
	
	public CallIdStatistic(String callID, Long quantity) {
		super();
		this.callID = callID;
		this.quantity = quantity;
	}

	public String getCallID() {
		return callID;
	}

	public void setCallID(String callID) {
		this.callID = callID;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}