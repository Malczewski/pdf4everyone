package ua.malczewski.pdf4everyone.rest.api.exceptions;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Slf4j
public class RestExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		log.error("Unhandled error during request", exception);
		String message = exception.getMessage();
		Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
		return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
	}
}
