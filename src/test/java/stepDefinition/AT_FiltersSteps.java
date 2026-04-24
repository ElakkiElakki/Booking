package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AT_FiltersPage;
import util.AllFunctionalities;

public class AT_FiltersSteps {

    private AT_FiltersPage filtersPage;
    public static String currentTestCaseId;

    @Given("user starts filter test case {string} and opens attractions page")
    public void user_starts_filter_test_case_and_opens_attractions_page(String testCaseId) {
        currentTestCaseId = testCaseId;
        System.out.println("Executing Test Case: " + currentTestCaseId);

        filtersPage = new AT_FiltersPage(AllFunctionalities.getDriver());
        filtersPage.openAttractionsPage();
    }

    @When("user enters destination {string}")
    public void user_enters_destination(String destination) {
        filtersPage.enterDestination(destination);
    }

    @When("user clicks attractions search button for filters")
    public void user_clicks_attractions_search_button_for_filters() {
        filtersPage.clickSearch();
        Assert.assertTrue(
                filtersPage.isResultsPageDisplayed(),
                "Attractions results page is not displayed."
        );
    }

    @When("user selects category filter {string}")
    public void user_selects_category_filter(String category) {
        filtersPage.selectCategoryFilter(category);
    }

    @When("user selects review score filter {string}")
    public void user_selects_review_score_filter(String score) {
        filtersPage.selectReviewScore(score);
    }

    @When("user selects time of day filter {string}")
    public void user_selects_time_of_day_filter(String time) {
        filtersPage.selectTimeOfDay(time);
    }

    @When("user clicks lowest price sort option")
    public void user_clicks_lowest_price_sort_option() {
        filtersPage.clickLowestPriceSort();
    }

    @Then("attractions results should be filtered by category {string}")
    public void attractions_results_should_be_filtered_by_category(String category) {
        Assert.assertTrue(
                filtersPage.isFilterSelected(category),
                "Category filter was not selected properly."
        );
    }

    @Then("attractions results should match selected filters")
    public void attractions_results_should_match_selected_filters() {
        Assert.assertTrue(
                filtersPage.areMultipleFiltersSelected(),
                "Multiple filters were not selected properly."
        );
    }

    @Then("attractions results should be sorted by lowest price")
    public void attractions_results_should_be_sorted_by_lowest_price() {
        Assert.assertTrue(
                filtersPage.isLowestPriceSelected(),
                "Lowest price sort option was not selected."
        );
    }
}