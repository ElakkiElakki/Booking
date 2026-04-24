package stepDefinition;

import io.cucumber.java.en.*;
import pages.SC04_FH_FlightsPage;
import util.AllFunctionalities;

public class SC04_FH_FlightsSteps {

    SC04_FH_FlightsPage flightsPage =
            new SC04_FH_FlightsPage(AllFunctionalities.getDriver());

    // ✅ Initialize safely
    @Given("initialize flight page")
    public void init_flight_page() {
        flightsPage = new SC04_FH_FlightsPage(AllFunctionalities.getDriver());
    }
    public void sleep() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ===== VERIFY PAGE =====
    @Then("user should see flight selection page")
    public void user_should_see_flight_selection_page() {
        flightsPage.verifyFlightPage();
    }

    // ===== FILTER & SORT TEST CASES =====

    @When("user clicks cheapest tab")
    public void user_clicks_cheapest_tab() {
        flightsPage.clickCheapest();
        sleep();  
        }

    @When("user clicks fastest tab")
    public void user_clicks_fastest_tab() {
        flightsPage.clickFastest();
        sleep();
    }
    @When("user selects best tab")
    public void user_selects_best_tab() {
        flightsPage.clickBest();
        sleep();
    }

    @When("user applies luggage filter")
    public void user_applies_luggage_filter() {
        flightsPage.clickLuggageFilter();
        sleep();
    }

    @When("user enables direct routes only")
    public void user_enables_direct_routes_only() {
        flightsPage.toggleDirectRoutes();
        sleep();
    }

    @When("user selects one stop flights")
    public void user_selects_one_stop_flights() {
        flightsPage.clickOneStop();
        sleep();
    }

    @When("user resets filters")
    public void user_resets_filters() {
        flightsPage.clickResetFilters();
        sleep();
    }

    
    // ===== EXISTING MAIN FLOW =====
    @When("user selects flight and clicks continue")
    public void user_selects_flight_and_clicks_continue() {
        flightsPage.selectFlightAndContinue();
    }

    @Then("traveller details page should be displayed")
    public void traveller_details_page_should_be_displayed() {
        flightsPage.verifyTravellerPage();
    }
}