package com.dj.cucumberspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = CucumberSpringApplication.class)
@WebAppConfiguration
@SpringBootTest
public class SpringIntegrationBaseTest {
	static ResponseResults latestResponse = null;

	@Autowired
	protected RestTemplate restTemplate;

	void executeGet(String url) throws IOException {
		final Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json");
		final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
		final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

		restTemplate.setErrorHandler(errorHandler);
		latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
			if (errorHandler.hadError) {
				return (errorHandler.getResults());
			} else {
				return (new ResponseResults(response));
			}
		});
	}

	void executePost() throws IOException {
		final Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json");
		final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
		final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		restTemplate.setErrorHandler(errorHandler);
		latestResponse = restTemplate
				.execute("http://localhost:8082/baeldung", HttpMethod.POST, requestCallback, response -> {
					if (errorHandler.hadError) {
						return (errorHandler.getResults());
					} else {
						return (new ResponseResults(response));
					}
				});
	}

	private class ResponseResultErrorHandler implements ResponseErrorHandler {
		private ResponseResults results = null;
		private Boolean hadError = false;

		private ResponseResults getResults() {
			return results;
		}

		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException {
			hadError = response.getRawStatusCode() >= 400;
			return hadError;
		}

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			results = new ResponseResults(response);
		}
	}
}
