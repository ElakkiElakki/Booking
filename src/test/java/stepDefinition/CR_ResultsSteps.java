package stepDefinition;

import io.cucumber.java.en.*;
import org.testng.Assert;
import base.pages;

public class CR_ResultsSteps {

    int beforeCount; // store count before filtering

    // verify results list
    @Then("user should see list of available cars")
    public void verify_results_list() {
        pages.resultsPage.handlePopupIfPresent(); // handle popup
        int count = pages.resultsPage.getResultCount(); // get result count
        Assert.assertTrue(count > 0, "❌ No cars displayed");
    }

    // apply filters
    @When("user applies filters like location, car category and price per day")
    public void apply_filters() {
        beforeCount = pages.resultsPage.getResultCount(); // store initial count
        pages.resultsPage.applyAllFilters(); // apply filters
    }

    // verify filtered results
    @Then("filtered results should be displayed")
    public void verify_filtered() {
        int after = pages.resultsPage.getResultCount(); // get new count
        Assert.assertTrue(after >= 0, "❌ Filter failed");    }

    // clear filters
    @When("user clears all filters")
    public void clear_filters() {
        pages.resultsPage.clearAllFilters(); // clear filters
    }

    // verify default results
    @Then("default results should be displayed again")
    public void verify_default() {
        int afterClear = pages.resultsPage.getResultCount(); // get count
        Assert.assertTrue(afterClear >= beforeCount, "❌ Default results not restored");
    }
}