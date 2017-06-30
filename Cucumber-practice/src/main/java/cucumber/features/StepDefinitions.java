package cucumber.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {

	WebDriver driver = null;
	
	@Given("^I navigate to Google\\.com website$")
	public void shouldNavigateToGoogleWeb() throws Throwable {
		System.setProperty("webdriver.gecko.driver", "c:\\sw\\geckodriver-v0.12.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
	    driver.navigate().to("http://www.google.co.in");
	}

	@When("^I enter text to search$")
	public void shouldSearchForSomething() throws Throwable {
		System.out.println("Enter some search text");
	}

	@When("^click search button$")
	public void clickSearchButton() throws Throwable {
		System.out.println("Click on search button");
	}

	@Then("^I see that some results are available$")
	public void checkResultsAreAvailable() throws Throwable {
		System.out.println("Check that 20 results are returned.");
		driver.close();
		driver.quit();
	}
	
}
