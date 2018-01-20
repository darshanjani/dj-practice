package com.dj.cucumberspring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = WebEnvironment.MOCK,
		classes = CucumberSpringApplication.class
)
public class CucumberSpringApplicationTests {

	MediaType contentType = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void givenRequestForVersionMade_ThenReturnsExpectedVersion() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/version"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType))
				.andExpect(MockMvcResultMatchers.content().string("1.0")
		);
	}

}
