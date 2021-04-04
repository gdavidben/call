package com.gdavidben.call.producer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Type;

import io.reactivex.Flowable;

@ApplicationScoped
public class CallProducer {

	@Outgoing("outcalls")
	public Flowable<Call> generateCalls() {
		return Flowable.interval(10, TimeUnit.SECONDS).map(t -> generateCall());
	}

	private LocalDate between(LocalDate start, LocalDate end) {
		long startSeconds = start.toEpochDay();
		long endSeconds = end.toEpochDay();
		long randomTime = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);

		return LocalDate.ofEpochDay(randomTime);
	}

	private LocalTime between(LocalTime startTime, LocalTime endTime) {
		int startSeconds = startTime.toSecondOfDay();
		int endSeconds = endTime.toSecondOfDay();
		int randomTime = ThreadLocalRandom.current().nextInt(startSeconds, endSeconds);

		return LocalTime.ofSecondOfDay(randomTime);
	}

	private Integer between(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	private Call generateCall() {
		LocalDate date = between(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));
		LocalTime time = between(LocalTime.of(0, 0, 0), LocalTime.of(23, 59, 59));
		LocalDateTime dateTime = date.atTime(time);

		Call call = new Call();
		call.setStart(dateTime);
		call.setEnd(dateTime.plusSeconds(between(2, 2000)));
		call.setCaller(between(900500500, 900500520).toString());
		call.setCallee(between(900600600, 900600620).toString());
		call.setType(between(1, 20) > 10 ? Type.INBOUND : Type.OUTBOUND);

		return call;
	}

}
