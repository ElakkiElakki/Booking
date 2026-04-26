package stepDefinition;

import io.cucumber.java.en.*;
import pages.FH_SearchNegative;   // ✅ correct import
import util.AllFunctionalities;

public class FH_SearchNegativeSteps {   // ✅ rename class (important)

    FH_SearchNegative page =
            new FH_SearchNegative(AllFunctionalities.getDriver());

    @And("FH user enters departure {string}")
    public void enter_departure(String dep) {

        if (dep == null || dep.trim().isEmpty()) {
            page.clearDepartureField();
        } else {
            page.enterDeparture(dep);
        }
    }

    @And("FH user enters destination {string}")
    public void enter_destination(String dest) {

        if (dest == null || dest.trim().isEmpty()) {
            page.clearDestinationField();
        } else {
            page.enterDestination(dest);
        }
    }
    @Then("system should handle invalid search result")
    public void validate_invalid() {
        page.verifyInvalidSearch();
    }
}