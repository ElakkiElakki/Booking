package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {
//				car rental
//			    "src/test/resources/features/CR_CarBooking.feature",
//			    "src/test/resources/features/CR_CarDetails.feature",
//			    "src/test/resources/features/CR_CarExtras.feature",
//			    "src/test/resources/features/CR_CarProtection.feature",
//			    "src/test/resources/features/CR_CarResults.feature",
//			    "src/test/resources/features/CR_CarSearch.feature"
				
//				 flights
//				 "src/test/resources/features/FH_EndToEnd.feature",
//				 "src/test/resources/features/FH_flights_hotels_search.feature",
//				 "src/test/resources/features/FH_flights.feature",
//		         "src/test/resources/features/FH_hotel_details.feature",
//				 "src/test/resources/features/FH_hotel_listing.feature",
//				 "src/test/resources/features/FH_SearchNegative.feature"
				
//				 attractions
				 "src/test/resources/features/AT_End_To_End.feature",
//				 "src/test/resources/features/AT_Fav.feature"
				
				
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