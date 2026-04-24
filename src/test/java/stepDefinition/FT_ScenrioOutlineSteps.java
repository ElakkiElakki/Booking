package stepDefinition;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import pages.FT_ScenrioOutlinePage;
import util.AllFunctionalities;

import java.time.Duration;

public class FT_ScenrioOutlineSteps {

    WebDriver driver = AllFunctionalities.getDriver();
    FT_ScenrioOutlinePage page = new FT_ScenrioOutlinePage(driver);

    @Given("user is on booking homepage")
    public void homepage() {

        driver.get(AllFunctionalities.getConfig("baseUrl"));

        // 🔥 wait for page load
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        page.openFlights();
    }

    @When("user enters departure {string} and destination {string}")
    public void user_enters_locations(String departure, String destination) {

        page.enterDeparture(departure);
        page.enterDestination(destination);
    }

    @When("user clicks search")
    public void user_clicks_search() {
        page.clickSearch();
    }

    @Then("user should see {string}")
    public void user_should_see(String expected) {
        page.validateResult(expected);
    }
}