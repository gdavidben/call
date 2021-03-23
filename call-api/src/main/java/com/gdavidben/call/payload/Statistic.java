package com.gdavidben.call.payload;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdavidben.call.api.CallProperties;

@Schema(name = "Statistic", description = "Object that represents a statistic about daily calls")
public class Statistic {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CallProperties.DATE_FORMAT)
	private LocalDate date;

	private Integer totalCalls;

	private Long totalInboundCallsDuration;

	private Double totalInboundCallsCost;
	
	private Long totalOutboundCallsDuration;

	private Double totalOutboundCallsCost;

	private List<CallIdStatistic> totalCallsByCaller;

	private List<CallIdStatistic> totalCallsByCallee;

	
	public Statistic() {
		super();
	}
	
	public Statistic(LocalDate date, Integer totalCalls, Long totalInboundCallsDuration, Double totalInboundCallsCost,
			Long totalOutboundCallsDuration, Double totalOutboundCallsCost, List<CallIdStatistic> totalCallsByCaller,
			List<CallIdStatistic> totalCallsByCallee) {
		super();
		this.date = date;
		this.totalCalls = totalCalls;
		this.totalInboundCallsDuration = totalInboundCallsDuration;
		this.totalInboundCallsCost = totalInboundCallsCost;
		this.totalOutboundCallsDuration = totalOutboundCallsDuration;
		this.totalOutboundCallsCost = totalOutboundCallsCost;
		this.totalCallsByCaller = totalCallsByCaller;
		this.totalCallsByCallee = totalCallsByCallee;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getTotalCalls() {
		return totalCalls;
	}

	public void setTotalCalls(Integer totalCalls) {
		this.totalCalls = totalCalls;
	}

	public Long getTotalInboundCallsDuration() {
		return totalInboundCallsDuration;
	}

	public void setTotalInboundCallsDuration(Long totalInboundCallsDuration) {
		this.totalInboundCallsDuration = totalInboundCallsDuration;
	}

	public Long getTotalOutboundCallsDuration() {
		return totalOutboundCallsDuration;
	}

	public void setTotalOutboundCallsDuration(Long totalOutboundCallsDuration) {
		this.totalOutboundCallsDuration = totalOutboundCallsDuration;
	}

	public Double getTotalInboundCallsCost() {
		return totalInboundCallsCost;
	}

	public void setTotalInboundCallsCost(Double totalInboundCallsCost) {
		this.totalInboundCallsCost = totalInboundCallsCost;
	}

	public Double getTotalOutboundCallsCost() {
		return totalOutboundCallsCost;
	}

	public void setTotalOutboundCallsCost(Double totalOutboundCallsCost) {
		this.totalOutboundCallsCost = totalOutboundCallsCost;
	}

	public List<CallIdStatistic> getTotalCallsByCaller() {
		return totalCallsByCaller;
	}

	public void setTotalCallsByCaller(List<CallIdStatistic> totalCallsByCaller) {
		this.totalCallsByCaller = totalCallsByCaller;
	}

	public List<CallIdStatistic> getTotalCallsByCallee() {
		return totalCallsByCallee;
	}

	public void setTotalCallsByCallee(List<CallIdStatistic> totalCallsByCallee) {
		this.totalCallsByCallee = totalCallsByCallee;
	}
	
	public static class Builder {

		private LocalDate date;

		private Integer totalCalls;

		private Long totalInboundCallsDuration;

		private Double totalInboundCallsCost;
		
		private Long totalOutboundCallsDuration;

		private Double totalOutboundCallsCost;

		private List<CallIdStatistic> totalCallsByCaller;

		private List<CallIdStatistic> totalCallsByCallee;

		
		public Builder withDate(LocalDate date) {
			this.date = date;
			return this;
		}

		public Builder withTotalCalls(Integer totalCalls) {
			this.totalCalls = totalCalls;
			return this;
		}

		public Builder withTotalInboundCallsDuration(Long totalInboundCallsDuration) {
			this.totalInboundCallsDuration = totalInboundCallsDuration;
			return this;
		}

		public Builder withTotalInboundCallsCost(Double totalInboundCallsCost) {
			this.totalInboundCallsCost = totalInboundCallsCost;
			return this;
		}

		public Builder withTotalOutboundCallsDuration(Long totalOutboundCallsDuration) {
			this.totalOutboundCallsDuration = totalOutboundCallsDuration;
			return this;
		}

		public Builder withTotalOutboundCallsCost(Double totalOutboundCallsCost) {
			this.totalOutboundCallsCost = totalOutboundCallsCost;
			return this;
		}

		public Builder withTotalCallsByCaller(List<CallIdStatistic> totalCallsByCaller) {
			this.totalCallsByCaller = totalCallsByCaller;
			return this;
		}

		public Builder withTotalCallsByCallee(List<CallIdStatistic> totalCallsByCallee) {
			this.totalCallsByCallee = totalCallsByCallee;
			return this;
		}

		public Statistic build() {
	        return new Statistic(date, totalCalls, totalInboundCallsDuration, totalInboundCallsCost, totalOutboundCallsDuration, totalOutboundCallsCost, totalCallsByCaller, totalCallsByCallee);
	    }
	    
	}

}