package stepDefinition;

import io.cucumber.java.en.*;
import pages.SC02_FH_HotelListingPage;
import util.AllFunctionalities;

public class SC02_FH_HotelListingSteps {

    SC02_FH_HotelListingPage hotelPage = new SC02_FH_HotelListingPage(AllFunctionalities.getDriver());

    // ✅ ONLY INITIALIZE PAGE
    @Given("user is on hotel listing page")
    public void user_on_hotel_listing_page() {
        hotelPage = new SC02_FH_HotelListingPage(AllFunctionalities.getDriver());
    }

    // ================= NEW STEPS =================

    @When("user toggles map view")
    public void user_toggles_map_view() throws InterruptedException {
        hotelPage.toggleMapDesktop();
    }

    @When("user applies 5 star filter")
    public void user_applies_5_star_filter() throws InterruptedException {
        hotelPage.applyFiveStarFilter();
    }

    @When("user sorts hotels by lowest price")
    public void user_sorts_hotels_by_lowest_price() throws InterruptedException {
        hotelPage.sortByLowestPrice();
    }

    // ================= EXISTING =================

    @When("user selects first hotel")
    public void user_selects_first_hotel() {
        hotelPage.selectFirstHotel();
    }

    @Then("user should see room details page")
    public void verify_room_page() {
        hotelPage.verifyRoomPage();
    }
}