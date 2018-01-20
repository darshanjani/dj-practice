package com.dj.cucumberspring;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VersionVerifierSteps extends SpringIntegrationMockBaseTest {

	public static final String VERSION_URL = "http://localhost:8082/version";

	@When("^the client calls /version$")
	public void theClientCallsVersion() throws Throwable {
		executeGet(VERSION_URL);
	}

	@Then("^the client receives status code of (\\d+)$")
	public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
		assertThat("status code is incorrect : "+
				getBody(), getStatusCode(), is(statusCode));
	}

	@And("^the client receives server version \"([^\"]*)\"$")
	public void theClientReceivesServerVersion(String version) throws Throwable {
		assertThat(getBody(), is(version));
	}

}
