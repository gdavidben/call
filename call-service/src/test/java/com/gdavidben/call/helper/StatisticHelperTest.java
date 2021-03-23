package com.gdavidben.call.helper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gdavidben.call.configuration.CallConfiguration;
import com.gdavidben.call.entity.CallEntity;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;

class StatisticHelperTest {

	private static Long SECS_OF_A_HOUR = 3600L;
	
	private static StatisticHelper statisticHelper;
	private static List<CallConfiguration.Rate> rates;
	private static List<CallEntity> calls;
	private static LocalDateTime now;

	static void loadRates() {
		CallConfiguration.Rate rateUntil5min = new CallConfiguration.Rate();
		rateUntil5min.setUntil(5);
		rateUntil5min.setCost(0.10D);
		
		CallConfiguration.Rate rateAfter5Min = new CallConfiguration.Rate();
		rateAfter5Min.setUntil(543200);
		rateAfter5Min.setCost(0.05D);
		
		rates = Arrays.asList(rateUntil5min, rateAfter5Min);
	}
	
	static void loadCalls() {
		CallEntity outboundCall10s = new CallEntity();
		outboundCall10s.setStart(now);
		outboundCall10s.setEnd(now.plusSeconds(10));
		outboundCall10s.setCaller("12");
		outboundCall10s.setCallee("34");
		outboundCall10s.setType(Type.OUTBOUND);

		CallEntity outboundCall70s = new CallEntity();
		outboundCall70s.setStart(now);
		outboundCall70s.setEnd(now.plusSeconds(70));
		outboundCall70s.setCaller("12");
		outboundCall70s.setCallee("34");
		outboundCall70s.setType(Type.OUTBOUND);

		CallEntity outboundCall10min = new CallEntity();
		outboundCall10min.setStart(now);
		outboundCall10min.setEnd(now.plusMinutes(10));
		outboundCall10min.setCaller("12");
		outboundCall10min.setCallee("34");
		outboundCall10min.setType(Type.OUTBOUND);

		CallEntity inboundCall1h = new CallEntity();
		inboundCall1h.setStart(now);
		inboundCall1h.setEnd(now.plusHours(1));
		inboundCall1h.setCaller("56");
		inboundCall1h.setCallee("78");
		inboundCall1h.setType(Type.INBOUND);

		calls = Arrays.asList(outboundCall10s, outboundCall70s, outboundCall10min, inboundCall1h);
	}
	
	@BeforeAll
	static void setup() {
		statisticHelper = new StatisticHelper();
		now = LocalDateTime.now();

		loadRates();
		loadCalls();
	}

	@Test
	void validateStatistics() {
		Statistic statistic = statisticHelper.generateStatistic(calls, rates);
		
		assertEquals(now.toLocalDate(), statistic.getDate(), "Date is wrong");
		
		assertTrue(calls.size() == statistic.getTotalCalls(), "Total calls is different");
		
		assertEquals(SECS_OF_A_HOUR, statistic.getTotalInboundCallsDuration(), "Total inbound calls duration is wrong");
		
		assertEquals(600+70+10, statistic.getTotalOutboundCallsDuration(), "Total outbound calls duration is wrong");
		
		assertEquals(0.0, statistic.getTotalInboundCallsCost(), "Total inbound calls cost is wrong");
		
		assertEquals(1.05, statistic.getTotalOutboundCallsCost(), "Total inbound calls cost is wrong");
		
		assertEquals(3L, statistic.getTotalCallsByCaller().stream().filter(c -> "12".equals(c.getCallID())).map(ci ->ci.getQuantity()).findFirst().get(), "Total calls by caller are wrong");
		
		assertEquals(3L, statistic.getTotalCallsByCallee().stream().filter(c -> "34".equals(c.getCallID())).map(ci ->ci.getQuantity()).findFirst().get(), "Total calls by callee are wrong");
		
	}

}
