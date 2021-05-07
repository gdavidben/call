package com.gdavidben.call.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.gdavidben.call.configuration.CallConfiguration;
import com.gdavidben.call.entity.CallEntity;
import com.gdavidben.call.payload.CallIdStatistic;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;

@ApplicationScoped
public class StatisticHelper {

	private static Long SECS_OF_A_MIN = 60L;
	
	@Inject
	CallConfiguration callRateConfiguration;
	
	private List<CallConfiguration.Rate> loadCallRates() {
		return callRateConfiguration.getRates().parallelStream()
											   .sorted(Comparator.comparing(CallConfiguration.Rate::getUntil))
											   .collect(Collectors.toList());
	}
	
	public List<Statistic> generateStatistics(List<CallEntity> calls) {
		
		List<CallConfiguration.Rate> rates = loadCallRates();
		
		Map<LocalDate, List<CallEntity>> group = calls.parallelStream().collect(Collectors.groupingBy(CallEntity::getLocalDateEnd, Collectors.toList()));
		
		return group.entrySet().parallelStream().map(map -> generateStatistic(map.getValue(), rates)).collect(Collectors.toList());
	}
	
	public Statistic generateStatistic(List<CallEntity> calls, List<CallConfiguration.Rate> rates) {
		if(calls == null || calls.isEmpty()) {
			return new Statistic.Builder().build();
		}
		
		return new Statistic.Builder().withDate(calls.get(0).getEnd().toLocalDate())
									  .withTotalCalls(calls.size())
									  .withTotalInboundCallsDuration(durationCalls(calls, Type.INBOUND))
									  .withTotalOutboundCallsDuration(durationCalls(calls, Type.OUTBOUND))
									  .withTotalInboundCallsCost(0D)
									  .withTotalOutboundCallsCost(roundedCallCosts(calls, Type.OUTBOUND, rates))
									  .withTotalCallsByCaller(totalByCallId(calls, CallEntity::getCaller))
									  .withTotalCallsByCallee(totalByCallId(calls, CallEntity::getCallee))
									  .build();
	}

	private Long durationCalls(List<CallEntity> calls, Type type) {
		return calls.parallelStream()
					.filter(c -> type.equals(c.getType()))
					.map(c -> Duration.between(c.getStart(), c.getEnd()).toSeconds())
					.reduce(0L, Long::sum);
	}
	
	private Double roundedCallCosts(List<CallEntity> calls, Type type, List<CallConfiguration.Rate> rates) {
		return round(callCosts(calls, type, rates));
	}
	
	private Double callCosts(List<CallEntity> calls, Type type, List<CallConfiguration.Rate> rates) {
		return calls.parallelStream()
					.filter(c -> type.equals(c.getType()))
					.map(c -> Duration.between(c.getStart(), c.getEnd()).toSeconds())
					.filter(s -> s > 0L)
					.map(s -> calculateCost(s, rates))
					.reduce(0D, Double::sum);
	}
	
	private Double calculateCost(Long remainderSecs, List<CallConfiguration.Rate> rates) {
		Double finalCost = 0D;
		Long remainderMins = secsToMins(remainderSecs);
		
		for (CallConfiguration.Rate rate : rates) {
			Integer until = rate.getUntil();
			Double cost = rate.getCost();
			
			if(remainderMins <= 0) {
				break;
			} else if(remainderMins <= until) {
				finalCost += cost * remainderMins;
				remainderMins = 0L;
			} else {
				finalCost += cost * until;
				remainderMins -= until;
			}
		}
		
		return finalCost;
	}
	
	private Long secsToMins(Long remainder) {
		Long mins;
		
		if(remainder % SECS_OF_A_MIN != 0 ) { 
			// e.g 10s = 1 min or 70s = 2m
			mins = Duration.ofSeconds(remainder).plusSeconds(SECS_OF_A_MIN).toMinutes();
		} else {
			mins = Duration.ofSeconds(remainder).toMinutes();
		}
		
		return mins;
	}
	
	private List<CallIdStatistic> totalByCallId(List<CallEntity> calls, Function<CallEntity, String> classifier) {
		Map<String, Long> group = calls.parallelStream().collect(Collectors.groupingBy(classifier, Collectors.counting()));
		
		return group.entrySet().parallelStream().map(m -> new CallIdStatistic(m.getKey(), m.getValue())).collect(Collectors.toList());
	}
	
	private Double round(Double value) {
		BigDecimal bigDecimal = BigDecimal.valueOf(value);
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
		
	    return bigDecimal.doubleValue();
	}
	
}
