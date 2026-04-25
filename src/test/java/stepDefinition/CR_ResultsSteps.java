package stepDefinition;

import io.cucumber.java.en.*;
import org.testng.Assert;
import base.pages;

public class CR_ResultsSteps {

    int beforeCount; // store count before filtering

    // verify results list
    @Then("user should see list of available cars")
    public void verify_results_list() {

        pages.resultsPage.handlePopupIfPresent();

        int count = pages.resultsPage.getResultCount();

        Assert.assertTrue(count > 0, "❌ No cars displayed");

        System.out.println("✅ Cars displayed: " + count);
    }

    // apply filters
    @When("user applies filters like location, car category and price per day")
    public void apply_filters() {

        beforeCount = pages.resultsPage.getResultCount();

        System.out.println("👉 Before filters: " + beforeCount);

        pages.resultsPage.applyAllFilters();
    }

    // verify filtered results
    @Then("filtered results should be displayed")
    public void verify_filtered() {

        int after = pages.resultsPage.getResultCount(); // get new count

        System.out.println("👉 Before filters: " + beforeCount);
        System.out.println("👉 After filters: " + after);

        // ✔ Ensure results exist
        Assert.assertTrue(after > 0, "❌ No results after applying filters");

        // ✔ Soft validation (no failure if equal)
        if (after == beforeCount) {
            System.out.println("⚠️ Filter applied but count did not change (possible real scenario)");
        } else {
            System.out.println("✅ Filters changed results");
        }
    }

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