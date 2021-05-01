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
	@QueryParam("delayMs")
	private long delayMs = 0;
	@QueryParam("indicatorVariable")
	private String indicatorVariable = null;
	@QueryParam("width")
	private Integer width;
	@QueryParam("height")
	private Integer height;
}
