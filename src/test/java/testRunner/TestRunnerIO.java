package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {
			    // Attractions (AT)
//			    "src/test/resources/features/AT_End_To_End.feature",
//			    "src/test/resources/features/AT_Fav.feature",
//			    "src/test/resources/features/AT_Filters.feature",
//			    "src/test/resources/features/AT_Gallery.feature",
//			    "src/test/resources/features/AT_Search.feature",

			    // Car Rental (CR)
//			    "src/test/resources/features/CR_CarBooking.feature",
//			    "src/test/resources/features/CR_CarDetails.feature",
//			    "src/test/resources/features/CR_CarExtras.feature",
//			    "src/test/resources/features/CR_CarProtection.feature",
//			    "src/test/resources/features/CR_CarResults.feature",
//			    "src/test/resources/features/CR_CarSearch.feature",

			    // Flights + Hotels (FH)
//			    "src/test/resources/features/FH_EndToEnd.feature",
//			    "src/test/resources/features/FH_flights_hotels_search.feature",
//			    "src/test/resources/features/FH_flights.feature",
//			    "src/test/resources/features/FH_hotel_details.feature",
//			    "src/test/resources/features/FH_hotel_listing.feature",
//			    "src/test/resources/features/FH_SearchNegative.feature",

			    // Flights (FT)
//			    "src/test/resources/features/FT_EndToEndBooking.feature",
//			    "src/test/resources/features/FT_FlightOptions.feature",
//			    "src/test/resources/features/FT_FoodAndSeatPreference.feature",
//			    "src/test/resources/features/FT_ScenrioOutline.feature",
//			    "src/test/resources/features/FT_SearchingFlights.feature",
//			    "src/test/resources/features/FT_TravellerDetails.feature",

			    // Stays (ST)
			    "src/test/resources/features/ST_Booking.feature",
			    "src/test/resources/features/ST_EndToEnd.feature",
			    "src/test/resources/features/ST_Filters.feature",
			    "src/test/resources/features/ST_HomeSearch.feature",
			    "src/test/resources/features/ST_HotelDetails.feature"
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