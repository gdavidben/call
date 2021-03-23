package com.gdavidben.call.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gdavidben.call.client.utils.HttpUtils;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;

class CallClientTest {

	private static CallClient callClient;
	private static List<Call> calls;

	public static LocalDate between(LocalDate start, LocalDate end) {
	    long startSeconds = start.toEpochDay();
	    long endSeconds = end.toEpochDay();
	    long randomTime = ThreadLocalRandom
	      .current()
	      .nextLong(startSeconds, endSeconds);

	    return LocalDate.ofEpochDay(randomTime);
	}
	
	public static LocalTime between(LocalTime startTime, LocalTime endTime) {
	    int startSeconds = startTime.toSecondOfDay();
	    int endSeconds = endTime.toSecondOfDay();
	    int randomTime = ThreadLocalRandom
	      .current()
	      .nextInt(startSeconds, endSeconds);

	    return LocalTime.ofSecondOfDay(randomTime);
	}
	
	public static Integer between(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	static void loadCalls() {
		calls = new ArrayList<>();
		
		for (int i = 0; i < 500; i ++) {
			LocalDate date = between(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));
			LocalTime time = between(LocalTime.of(0, 0, 0), LocalTime.of(23, 59, 59));
			LocalDateTime dateTime = date.atTime(time);
					
			Call call = new Call();
			call.setStart(dateTime);
			call.setEnd(dateTime.plusSeconds(between(2, 2000)));
			call.setCaller(between(900500500, 900500520).toString());
			call.setCallee(between(900600600, 900600620).toString());
			call.setType(between(1, 20) > 10 ? Type.INBOUND : Type.OUTBOUND);
			
			calls.add(call);
		}
	}

	@BeforeAll
	static void setup() {
		callClient = new CallClient.Builder().build();
		
		loadCalls();
	}

	@Test
	void validateCallClient() {
		Response responseCreateCalls = callClient.createCalls(calls);
		assertEquals(Response.Status.CREATED.getStatusCode(), responseCreateCalls.getStatus(), "Error in createCalls status response");

		List<Call> createdCalls = HttpUtils.toList(responseCreateCalls.getEntity(), Call.class);
		assertEquals(calls.size(), createdCalls.size(), "");

		Response responseCreateCall = callClient.createCall(calls.get(0));
		Call createdCall = HttpUtils.toObject(responseCreateCall.getEntity(), Call.class);
		
		assertEquals(Response.Status.CREATED.getStatusCode(), responseCreateCall.getStatus(), "Error in createCall status response");
		assertNotNull(createdCall.getId(), "Error in createCall body response");
		
		Response responseListCalls = callClient.listCalls(null, 0, 50);
		assertEquals(Response.Status.OK.getStatusCode(), responseListCalls.getStatus(), "Error in listCalls status response");
		
		Response responseStatisticsCall = callClient.statisticsCall();
		assertEquals(Response.Status.OK.getStatusCode(), responseStatisticsCall.getStatus(), "Error in statisticsCall status response");
		
		List<Statistic> statistics = HttpUtils.toList(responseStatisticsCall.getEntity(), Statistic.class);
		assertNotNull(statistics, "Error in statisticsCall body response");
		
		Response responseDeleteCall = callClient.deleteCall(createdCall.getId());
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), responseDeleteCall.getStatus(), "Error in deleteCall status response");
		
		createdCalls.stream().forEach(c -> callClient.deleteCall(c.getId()));
	}
	
}
