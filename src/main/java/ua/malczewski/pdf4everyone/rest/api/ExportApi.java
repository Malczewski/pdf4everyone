package ua.malczewski.pdf4everyone.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.malczewski.pdf4everyone.browser.BrowserService;
import ua.malczewski.pdf4everyone.rest.api.dto.ExportParameters;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/export")
@Component
public class ExportApi {

	@Autowired
	BrowserService browserService;

	@GET
	@Path("/screenshot")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getScreenshot(@BeanParam ExportParameters parameters) {
		return fileResponse("page.png", browserService.exportScreenshot(parameters));
	}

	@GET
	@Path("/pdf")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getPDF(@BeanParam ExportParameters parameters) {
		return fileResponse("page.pdf", browserService.exportPDF(parameters));
	}

	private Response fileResponse(String filename, byte[] data) {
		return Response.status(200).entity(data)
				.type(MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", String.format("attachment; filename=\"%s\"", filename))
				.header("file-name", filename).build();
	}
}
