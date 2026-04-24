package stepDefinition;

import io.cucumber.java.en.*;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import base.pages;

public class CR_SearchSteps {

    public static String currentTestCaseId; // store test case id

    // start test case and open site
    @Given("user starts test case {string} on car rental page")
    public void start_test_case(String testCaseId) {
        currentTestCaseId = testCaseId;
        System.out.println("🚀 Executing Test Case: " + testCaseId);
        pages.searchPage.openCarRentalPage(); // open car rental page
    }

    // enter pickup value
    @When("user enters {string} pickup as {string}")
    public void enter_pickup(String type, String value) {

        if (type.equalsIgnoreCase("valid")) {
            pages.searchPage.enterPickup(value); // enter valid pickup
        } else if (type.equalsIgnoreCase("empty")) {
            System.out.println("Skipping pickup"); // skip pickup
        } else {
            throw new RuntimeException("Invalid pickup type"); // error
        }
    }

    // enter drop value
    @When("user enters {string} drop as {string}")
    public void enter_drop(String type, String value) {

        if (type.equalsIgnoreCase("valid")) {
            pages.searchPage.clickCheckbox(); // enable drop field
            pages.searchPage.enterDrop(value); // enter drop
            pages.searchPage.selectDate(); // select dates
        } else if (type.equalsIgnoreCase("invalid")) {
            pages.searchPage.clickCheckbox(); // enable drop
            pages.searchPage.enterDrop(value); // invalid drop
        } else if (type.equalsIgnoreCase("empty")) {
            System.out.println("Skipping drop"); // skip
        } else {
            throw new RuntimeException("Invalid drop type"); // error
        }
    }

    // click search button
    @When("user clicks car rental search button")
    public void click_search() {
        pages.searchPage.clickSearch(); // click search
    }

    // validate result page
    @Then("car rental result should be {string}")
    public void verify_result(String expected) {

    	String url = pages.searchPage.getCurrentUrl();

    	if (expected.equalsIgnoreCase("success")) {

    	    new WebDriverWait(pages.searchPage.driver, Duration.ofSeconds(20))
    	            .until(ExpectedConditions.urlContains("search-results"));

    	    Assert.assertTrue(url.contains("search-results"),
    	            "❌ Results page not displayed");

    	} else {
    	    Assert.assertFalse(url.contains("search-results"),
    	            "❌ Validation failed");
    	}
    }
}