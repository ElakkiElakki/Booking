package stepDefinition;

import io.cucumber.java.en.*;
import pages.FH_HotelDetailsPage;
import util.AllFunctionalities;

public class FH_HotelDetailsSteps {
	FH_HotelDetailsPage detailsPage =
	        new FH_HotelDetailsPage(AllFunctionalities.getDriver());

    // ✅ Initialize safely
    @Given("initialize hotel details page")
    public void init_details_page() {
        detailsPage = new FH_HotelDetailsPage(AllFunctionalities.getDriver());
    }
    @When("user clicks view map and closes it")
    public void user_clicks_view_map_and_closes_it() {
        detailsPage.handleMapFlow();
    }

    @When("user clicks reviews and returns to top")
    public void user_clicks_reviews_and_returns_to_top() {
        detailsPage.handleReviewsFlow();
    }

    @When("user clicks facilities and returns to top")
    public void user_clicks_facilities_and_returns_to_top() {
        detailsPage.handleFacilitiesFlow();
    }
    @When("user selects room and clicks continue")
    public void user_selects_room_and_continue() {
        detailsPage.selectRoomAndContinue();
    }

    @Then("flight selection page should be displayed")
    public void flight_selection_page_should_be_displayed() {
        detailsPage.verifyFlightPage();
    }
}