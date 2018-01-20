package com.dj.cucumberspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@SpringBootTest(
//		classes = CucumberSpringApplication.class,
//		webEnvironment = SpringBootTest.WebEnvironment.MOCK
//)
@ContextConfiguration(classes = CucumberSpringApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class SpringIntegrationMockBaseTest {
	int statusCode = -1;
	String body = null;

	@Autowired
	MockMvc mockMvc;

	void executeGet(String url) throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		body = response.getContentAsString();
		statusCode = response.getStatus();
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}
}
