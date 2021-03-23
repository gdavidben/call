package com.gdavidben.call.configuration;

import java.util.List;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "call")
public class CallConfiguration {

	private Integer statisticsCache;
	
	private List<Rate> rates;

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public Integer getStatisticsCache() {
		return statisticsCache;
	}

	public void setStatisticsCache(Integer statisticsCache) {
		this.statisticsCache = statisticsCache;
	}



	public static class Rate {
		private Integer until;
		private Double cost;

		public Integer getUntil() {
			return until;
		}

		public void setUntil(Integer until) {
			this.until = until;
		}

		public Double getCost() {
			return cost;
		}

		public void setCost(Double cost) {
			this.cost = cost;
		}

	}

}
