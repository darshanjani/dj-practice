package cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
	plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-html-report"},
	features = { "src/main/java/cucumber"}
)
public class CucumberRunner {

}
