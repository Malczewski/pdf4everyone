package ua.malczewski.pdf4everyone.rest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.ws.rs.QueryParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportParameters {
	@QueryParam("url")
	@NonNull
	private String url;
	@QueryParam("delaySeconds")
	private int delaySeconds = 0;
	@QueryParam("indicatorVariable")
	private String indicatorVariable = null;
	@QueryParam("width")
	@NonNull
	private Integer width;
	@QueryParam("height")
	@NonNull
	private Integer height;
}
