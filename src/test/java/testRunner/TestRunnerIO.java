package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {
			    // Attractions (AT)
			    "src/test/resources/features/AT_End_To_End.feature",
			    "src/test/resources/features/AT_Fav.feature",
			    "src/test/resources/features/AT_Filters.feature",
			    "src/test/resources/features/AT_Gallery.feature",
			    "src/test/resources/features/AT_Search.feature",

			    
			},
		glue = {"stepDefinition"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"
        },
        monochrome = true
)
public class TestRunnerIO extends AbstractTestNGCucumberTests {
}