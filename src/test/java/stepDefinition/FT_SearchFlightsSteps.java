package stepDefinition;

import io.cucumber.java.en.*;

import org.openqa.selenium.WebDriver;
import pages.FT_SearchFlightsPage;
import util.AllFunctionalities;

public class FT_SearchFlightsSteps {

	WebDriver driver = AllFunctionalities.getDriver();
	FT_SearchFlightsPage page = new FT_SearchFlightsPage(driver);


	@When("user performs flight search validations")
	public void runValidation(io.cucumber.datatable.DataTable table) throws InterruptedException {

		page.openFlights();
		page.selectOneWay();

		java.util.List<java.util.Map<String, String>> dataList = table.asMaps();

		for (java.util.Map<String, String> data : dataList) {

			String departure = data.get("departure");
			String destination = data.get("destination");
			String expected = data.get("expected");

			// ✅ Reset ONCE
			page.resetFields();

			// ✅ Fill inputs
			if (departure != null && !departure.isEmpty()) {
				page.enterDeparture(departure);
			}

			if (destination != null && !destination.isEmpty()) {
				page.enterDestination(destination);
			}

			// ✅ Date BEFORE search
			page.selectDate();

			page.clickSearch();

			// ✅ Validation
			switch (expected) {

			case "missing_departure":
				page.verifyMissingDeparture();
				break;

			case "missing_destination":
				page.verifyMissingDestination();
				break;

			case "success":
				page.verifyResults();
				break;
			}

			Thread.sleep(2000); // optional stability
		}
	}
}