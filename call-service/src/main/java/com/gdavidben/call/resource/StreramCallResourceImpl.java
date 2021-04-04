package com.gdavidben.call.resource;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import com.gdavidben.call.api.CallProperties;
import com.gdavidben.call.payload.Call;

@Path(CallProperties.CALL_RESOURCE_PATH)
public class StreramCallResourceImpl {

	private static final Logger LOG = Logger.getLogger(StreramCallResourceImpl.class);

	@Inject
    @Channel("incalls") Publisher<Call> calls; 

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS) 
    @SseElementType("text/plain") 
    public Publisher<Call> stream() { 
        return calls;
    }
	
}