package stepDefinition;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.*;
import pages.FT_EndToEndBookingPage;
import util.AllFunctionalities;

import java.util.List;
import java.util.Map;

public class FT_EndToEndBookingSteps {

	WebDriver driver = AllFunctionalities.getDriver();
	FT_EndToEndBookingPage flightPage = new FT_EndToEndBookingPage(driver);

	@When("user chooses one way trip option")
	public void user_chooses_one_way() {
		flightPage.selectOneWay();
	}

	// 🔥 ONLY LOCATIONS FROM DATATABLE
	@When("user enters flight locations")
	public void user_enters_flight_locations(io.cucumber.datatable.DataTable table) {

		Map<String, String> data = table.asMaps().get(0);

		flightPage.enterLocations(data.get("from1"), data.get("from2"), data.get("to"));
	}

	// 🔥 HARDCODED DATE + TRAVELLERS
	@When("user selects travel date and travellers")
	public void user_selects_date_and_travellers() {
		flightPage.selectDateAndTravellers();
	}

	@When("user triggers flight search")
	public void user_triggers_flight_search() {
		flightPage.clickSearch();
	}

	@Then("user views available flight results")
	public void user_views_available_flights() {
		flightPage.verifyResults();
	}

	@Then("user selects first flight and proceeds")
	public void user_selects_first_flight() {
		flightPage.openFirstFlightDetails();
		flightPage.clickContinueFromDetails();
		flightPage.selectTicketAndContinue();
	}

	@Then("user lands on traveller details section")
	public void user_lands_on_traveller_section() {
		System.out.println("Reached traveller details page");
	}

	@When("user enters traveller details")
	public void user_enters_traveller_details(io.cucumber.datatable.DataTable table) {

		List<Map<String, String>> dataList = table.asMaps();
		flightPage.fillTravellerDetailsFromData(dataList);
	}

	@When("user enters contact details")
	public void user_enters_contact_details(io.cucumber.datatable.DataTable table) {

		Map<String, String> data = table.asMaps().get(0);

		flightPage.fillContactDetailsFromData(data.get("email"), data.get("country"), data.get("phone"));
	}

	@When("user selects ticket type and proceeds")
	public void user_selects_ticket_type_and_proceeds() {
		flightPage.selectTicketType();
		flightPage.clickNextAfterTicket();
	}

	@When("user skips seat selection")
	public void user_skips_seat_selection() {
		flightPage.skipSeatSelection();
		flightPage.verifyPaymentPage();
	}

}