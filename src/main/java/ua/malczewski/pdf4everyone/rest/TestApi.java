package ua.malczewski.pdf4everyone.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "works!";
	}
}
