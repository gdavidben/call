package com.gdavidben.call;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.gdavidben.call.api.CallProperties;

@ApplicationPath(CallProperties.APPLICATION_PATH)
@OpenAPIDefinition(tags = {
		@Tag(name = CallProperties.TAG_CALL_NAME, description = CallProperties.TAG_CALL_DESCRIPTION) }, 
			 info = @Info(title = CallProperties.APPLICATION_TITLE, description = CallProperties.APPLICATION_DESCRIPTION, version = CallProperties.APPLICATION_VERSION), 
			 servers = { @Server(url = CallProperties.APPLICATION_SERVER_URL) })
public class CallApplication extends Application {

}