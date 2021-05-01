package ua.malczewski.pdf4everyone.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import ua.malczewski.pdf4everyone.rest.api.ExportApi;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

@Configuration
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
		register(JacksonFeature.class);
		register(ObjectMapperProvider.class);
		register(ExportApi.class);

	}
}
