package cucumber.features;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PageTitleDefinitions {
	
	WebDriver driver = null;

	@Before
	public void before() {
		System.out.println("In cucumber before method");
	}
	
	@After
	public void after() {
		System.out.println("In cucumber after method");
	}
	
	@Before("@web")
	public void beforeWeb() {
		System.out.println("In cucumber web before method");
		System.setProperty("webdriver.gecko.driver", "c:\\sw\\geckodriver-v0.12.0-win64\\geckodriver.exe");
	    driver = new FirefoxDriver();
	}
	
	@After("@web")
	public void afterWeb() {
		System.out.println("In cucumber web after method");
		driver.quit();
	}

	@Given("^I on the the selenium practice website$")
	public void i_on_the_the_selenium_practice_website() throws Throwable {
		
	    driver.navigate().to("http://www.practiceselenium.com/");
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@When("^I navigate to \"([^\"]*)\"$")
	public void i_navigate_to(String anchorText) throws Throwable {
		driver.findElement(By.linkText(anchorText)).click();
	}
	
	@Then("^I check page title is \"([^\"]*)\"$")
	public void i_check_page_title_is(String title) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains(title));
		assertTrue(driver.getTitle().contains(title));
	}
	
	@When("^I navigate to ([^\"]*)$")
	public void scenarioOutlineNavigation(String anchorText) throws Throwable {
		driver.findElement(By.linkText(anchorText)).click();
	}
	
	@Then("^I check page title is ([^\"]*)$")
	public void scenarioOutlineTitleCheck(String title) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains(title));
		assertTrue(driver.getTitle().contains(title));
	}
	
	@Then("^I close the browser$")
	public void i_close_the_browser() throws Throwable {
		driver.close();
	}
	
	@When("^Fill the Feedback form$")
	public void fill_the_Feedback_form(DataTable table) throws Throwable {
		List<List<String>> data = table.raw();
		
		System.out.println(data.get(1).get(1));
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By xpath = By.xpath("//h2[contains(text(),'Send us an email')]");
		wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
		
		driver.findElement(By.name("name")).sendKeys(data.get(1).get(1));
		driver.findElement(By.name("email")).sendKeys(data.get(2).get(1));
		driver.findElement(By.name("subject")).sendKeys(data.get(3).get(1));
		driver.findElement(By.name("message")).sendKeys(data.get(4).get(1));
		
		driver.findElement(By.xpath("//input[@value='Submit' and @type='submit']")).click();
	}

	@Then("^I verify if form is submitted$")
	public void i_verify_if_form_is_submitted() throws Throwable {
	    System.out.println("verify if something is done");
	}


}
