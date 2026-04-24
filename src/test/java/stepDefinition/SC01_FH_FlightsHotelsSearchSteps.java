package stepDefinition;

import io.cucumber.java.en.*;
import pages.SC01_FH_FlightsHotelsSearchPage;
import util.AllFunctionalities;

public class SC01_FH_FlightsHotelsSearchSteps {

    SC01_FH_FlightsHotelsSearchPage searchPage =
            new SC01_FH_FlightsHotelsSearchPage(AllFunctionalities.getDriver());

    @When("user enters departure location from excel row {int}")
    public void enter_departure(int row) {

        Object[][] data = AllFunctionalities.getData("combo");

        String dep = data[row - 1][1].toString().trim();

        searchPage.enterDeparture(dep);
    }

    @And("user enters destination location from excel row {int}")
    public void enter_destination(int row) {

        Object[][] data = AllFunctionalities.getData("combo");

        String dest = data[row - 1][2].toString().trim();

        searchPage.enterDestination(dest);
    }

    @Then("user should see hotel results")
    public void verify_results() {
        searchPage.verifyResults();
    }
}