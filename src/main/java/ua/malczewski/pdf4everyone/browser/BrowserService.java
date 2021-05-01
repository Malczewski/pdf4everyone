package ua.malczewski.pdf4everyone.browser;

import ua.malczewski.pdf4everyone.rest.api.dto.ExportParameters;

public interface BrowserService {
	byte[] exportScreenshot(ExportParameters parameters);
	byte[] exportPDF(ExportParameters parameters);
}
