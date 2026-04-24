package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AT_SearchPage;
import util.AllFunctionalities;

public class AT_SearchSteps {

    private AT_SearchPage searchPage;
    public static String currentTestCaseId;

    @Given("user starts test case {string} on attractions page")
    public void user_starts_test_case_on_attractions_page(String testCaseId) {
        currentTestCaseId = testCaseId;
        System.out.println("Executing Test Case: " + currentTestCaseId);

        searchPage = new AT_SearchPage(AllFunctionalities.getDriver());
        searchPage.openAttractionsPage();
    }

    @When("user enters {string} destination as {string}")
    public void user_enters_destination_as(String destinationType, String destinationValue) {
        if (destinationType.equalsIgnoreCase("valid")) {
            searchPage.enterDestination(destinationValue);
            searchPage.selectSuggestionIfPresent();
        } else if (destinationType.equalsIgnoreCase("empty")) {
            searchPage.clearDestination();
        } else if (destinationType.equalsIgnoreCase("invalid")) {
            searchPage.enterDestination(destinationValue);
        } else {
            throw new RuntimeException("Unsupported destination type: " + destinationType);
        }
    }

    @When("user clicks attractions search button")
    public void user_clicks_attractions_search_button() {
        searchPage.clickSearch();
    }

    @Then("attractions result should be {string}")
    public void attractions_result_should_be(String expectedResult) {
        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(
                    searchPage.isResultPageDisplayed(),
                    "Expected attractions results page to be displayed."
            );
        } else if (expectedResult.equalsIgnoreCase("validation")) {
            Assert.assertTrue(
                    searchPage.isValidationDisplayed() || !searchPage.isResultPageDisplayed(),
                    "Expected validation message or blocked search."
            );
        } else {
            throw new RuntimeException("Unsupported expected result: " + expectedResult);
        }
    }
}