package com.gdavidben.call.api;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;

public interface CallResource {

	@POST
	@Tag(ref = CallProperties.TAG_CALL_NAME)
	@Operation(summary = "Create a new call")
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = "Call created", content = @Content(schema = @Schema(implementation = Call.class))),
			@APIResponse(responseCode = "400", description = "Validation error"),
			@APIResponse(responseCode = "500", description = "General server error") })
	public Response createCall(Call call);

	@POST
	@Path("/bulk")
	@Tag(ref = CallProperties.TAG_CALL_NAME)
	@Operation(summary = "Create one or more calls")
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = "Call created", content = @Content(schema = @Schema(implementation = Call.class, type = SchemaType.ARRAY))),
			@APIResponse(responseCode = "400", description = "Validation error"),
			@APIResponse(responseCode = "500", description = "General server error") })
	public Response createCalls(List<Call> calls);
	
	@DELETE
	@Path("{id}")
	@Tag(ref = CallProperties.TAG_CALL_NAME)
	@Operation(summary = "Delete an existing call")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Call deleted", content = @Content(schema = @Schema(implementation = Call.class))),
			@APIResponse(responseCode = "400", description = "Validation error"),
			@APIResponse(responseCode = "404", description = "Call not found"),
			@APIResponse(responseCode = "500", description = "General server error") })
	public Response deleteCall(@Parameter(description = "Call identifier", required = true) @PathParam("id") String id);

	@GET
	@Path("{id}")
	@Tag(ref = CallProperties.TAG_CALL_NAME)
	@Operation(summary = "Get an existing call")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Call", content = @Content(schema = @Schema(implementation = Call.class))),
			@APIResponse(responseCode = "404", description = "Call not found"),
			@APIResponse(responseCode = "500", description = "General server error") })
	public Response findCall(@Parameter(description = "Call identifier", required = true) @PathParam("id") String id);
	
	@GET
	@Tag(ref = CallProperties.TAG_CALL_NAME)
	@Operation(summary = "Return the calls")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Calls", content = @Content(schema = @Schema(implementation = Call.class, type = SchemaType.ARRAY))),
			@APIResponse(responseCode = "500", description = "General server error") })
	public Response listCalls(@QueryParam("type") Type type, @DefaultValue("0") @QueryParam("offset") Integer offset, @DefaultValue("50") @QueryParam("limit") Integer limit);
	
	@GET
	@Path("/statistics")
	@Tag(ref = CallProperties.TAG_CALL_NAME)
	@Operation(summary = "Return statistics about calls")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Calls", content = @Content(schema = @Schema(implementation = Statistic.class, type = SchemaType.ARRAY))),
			@APIResponse(responseCode = "500", description = "General server error") })
	public Response statisticsCall();
}
