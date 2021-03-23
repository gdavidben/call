package com.gdavidben.call.resource;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.gdavidben.call.api.CallProperties;
import com.gdavidben.call.api.CallResource;
import com.gdavidben.call.configuration.CallConfiguration;
import com.gdavidben.call.exception.RequestException;
import com.gdavidben.call.exception.ResourceNotFoundException;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;
import com.gdavidben.call.service.CallService;
import com.gdavidben.call.utils.ResponseUtils;

@Path(CallProperties.CALL_RESOURCE_PATH)
public class CallResourceImpl implements CallResource {

	private static final Logger LOG = Logger.getLogger(CallResourceImpl.class);

	@Inject
	CallService callService;
	@Inject
	CallConfiguration callConfiguration;
	
	
	public Response createCall(Call call) {
		try {
			return ResponseUtils.created(callService.create(call));
		} catch (RequestException e) {
			LOG.warn(e);
			return ResponseUtils.badRequest(e.getValidations());
		} catch (Exception e) {
			LOG.error(e);
			return ResponseUtils.serverError(e.getMessage());
		}
	}

	public Response createCalls(List<Call> calls) {
		try {
			LocalTime start = LocalTime.now();
			List<Call> responseCalls = callService.createBulk(calls);
			LocalTime end = LocalTime.now();
			
			LOG.info(String.format("Bulk in %dms", Duration.between(start, end).toMillis()));
			return ResponseUtils.created(responseCalls);
		} catch (RuntimeException e) {
			LOG.warn(e);
			return ResponseUtils.badRequest(e.getMessage());
		}
		catch (Exception e) {
			LOG.error(e);
			return ResponseUtils.serverError(e.getMessage());
		}
	}
	
	public Response deleteCall(String id) {
		try {
			callService.delete(id);

			return ResponseUtils.noContent();
		} catch (ResourceNotFoundException e) {
			LOG.warn(e);
			return ResponseUtils.notFound();
		} catch (Exception e) {
			LOG.error(e);
			return ResponseUtils.serverError(e.getMessage());
		}
	}

	public Response findCall(String id) {
		try {
			return ResponseUtils.ok(callService.find(id));
		} catch (ResourceNotFoundException e) {
			LOG.warn(e);
			return ResponseUtils.notFound();
		} catch (Exception e) {
			LOG.error(e);
			return ResponseUtils.serverError(e.getMessage());
		}
	}
	
	public Response listCalls(Type type, Integer offset, Integer limit) {
		try {
			return ResponseUtils.ok(callService.list(type, offset, limit));
		} catch (Exception e) {
			LOG.error(e);
			return ResponseUtils.serverError(e.getMessage());
		}
	}

	@Override
	public Response statisticsCall() {
		try {
			LocalTime start = LocalTime.now();
			List<Statistic> statistics = callService.statistics();
			LocalTime end = LocalTime.now();
			
			LOG.info(String.format("Statistics in %dms", Duration.between(start, end).toMillis()));
			
			return ResponseUtils.ok(statistics, callConfiguration.getStatisticsCache());
		} catch (Exception e) {
			LOG.error(e);
			return ResponseUtils.serverError(e.getMessage());
		}
	}
	
	

}