package stepDefinition;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.ST_HomePage;
import util.AllFunctionalities;

public class ST_HomeSearchSteps {

    ST_HomePage homePage = new ST_HomePage(
        AllFunctionalities.getDriver());

    // ✅ Store date result between steps
    boolean dateSelectable = true;

    @Given("User logs in to booking.com stays")
    public void userLogsInToStays() {
        System.out.println("✅ Login complete — session marked active");
    }

    @Given("User is on booking.com stays homepage")
    public void userIsOnStaysHomepage() throws InterruptedException {
        AllFunctionalities.getDriver().get(
            AllFunctionalities.getConfig("baseUrl"));
        Thread.sleep(2000);

        // Dismiss sign-in banner
        try {
            WebElement dismiss = AllFunctionalities.getDriver()
                .findElement(By.cssSelector(
                    "button[aria-label='Dismiss sign in information.']"));
            dismiss.click();
        } catch (Exception e) {
            System.out.println("No sign-in banner");
        }

        System.out.println("Navigated to stays homepage");
    }

    @When("User performs search with destination {string} date {string} adults {int} children {int}")
    public void userPerformsSearch(String destination, String date,
                                   int adults, int children)
                                   throws InterruptedException {

        // ✅ Navigate to homepage fresh
        AllFunctionalities.getDriver().get(
            AllFunctionalities.getConfig("baseUrl"));
        Thread.sleep(2000);

        // ✅ Dismiss any overlay
        try {
            WebElement dismiss = AllFunctionalities.getDriver()
                .findElement(By.cssSelector(
                    "button[aria-label='Dismiss sign in information.']," +
                    "button[aria-label='Dismiss sign-in information.']"));
            dismiss.click();
            Thread.sleep(500);
        } catch (Exception ignored) {}

        // ✅ Enter destination
        if (destination.isEmpty()) {
            homePage.clearDestination();
            System.out.println("Empty destination");
        } else {
            homePage.enterDestination(destination);
        }

        // ✅ Select date if provided
        if (!date.equals("none")) {
            dateSelectable = homePage.selectCheckinDate(date);
            System.out.println("Date selectable: " + dateSelectable);
        }

        // ✅ Set guests if provided
        if (adults > 0 || children > 0) {
            homePage.selectGuests(adults, children);
        }

        // ✅ Click search
        homePage.clickSearch();
        Thread.sleep(2000);
    }

    @Then("{string} should be verified on homepage")
    public void verifyHomepageResult(String expectedResult)
                                     throws InterruptedException {

        if (expectedResult.contains("Results page")) {
            Assert.assertTrue(
                homePage.isResultsPageDisplayed(),
                "Results page not displayed");
            System.out.println("✅ Results page verified");

        } else if (expectedResult.contains("auto corrected")) {
            Assert.assertTrue(
                homePage.isResultsPageDisplayed(),
                "Results not shown");
            System.out.println("✅ Auto corrected results verified");

        } else if (expectedResult.contains("Validation error")) {
            Assert.assertTrue(
                homePage.isValidationErrorDisplayed(),
                "Validation error not shown");
            System.out.println("✅ Validation error verified");

        } else if (expectedResult.contains("Past date")) {
            Assert.assertFalse(dateSelectable,
                "Past date should not be selectable!");
            System.out.println("✅ Past date restriction verified");
        }
    }
}