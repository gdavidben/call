package com.gdavidben.call.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@LogTime
@Interceptor
@Priority(20)
public class LogTimeInterceptor {

	private static final Logger LOG = Logger.getLogger(LogTimeInterceptor.class);

	@Inject
	Event<LogTimeDetail> eventLogTimeDetail;

	@ConfigProperty(name = "call.max-time-response", defaultValue = "5")
	Long maxTimeResponse;

	@AroundInvoke
	Object invocation(InvocationContext context) throws Exception {
		LocalTime start = LocalTime.now();

		Object response = context.proceed();

		LocalTime end = LocalTime.now();

		afterInvocation(context, start, end);

		return response;
	}

	private void afterInvocation(InvocationContext context, LocalTime start, LocalTime end) {
		String method = context.getMethod().getName();
		
		Long duration = Duration.between(start, end).toMillis();

		LOG.info(String.format("%s in %dms", method, duration));

		if (duration > secToMillisec(maxTimeResponse)) {
			eventLogTimeDetail.fire(new LogTimeDetail(method, duration, LocalDateTime.now()));
		}
	}
	
	private long secToMillisec(Long sec) {
		return sec * 1000;
	}

}
