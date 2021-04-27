package ua.malczewski.pdf4everyone.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.ext.ContextResolver;

public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		return new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.registerModule(new JavaTimeModule())
				.setDateFormat(new StdDateFormat());
	}
}
