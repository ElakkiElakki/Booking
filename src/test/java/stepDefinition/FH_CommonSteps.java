package stepDefinition;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.*;
import pages.SC01_FH_FlightsHotelsSearchPage;
import util.AllFunctionalities;

public class FH_CommonSteps {

    SC01_FH_FlightsHotelsSearchPage searchPage =
            new SC01_FH_FlightsHotelsSearchPage(AllFunctionalities.getDriver());

    @Given("user is on booking homepage after login")
    public void user_on_homepage_after_login() {

        AllFunctionalities.launchBrowser();   // 🔥 ADD THIS

        WebDriver driver = AllFunctionalities.getDriver();

        driver.get("https://www.booking.com");   // 🔥 ADD THIS

        searchPage = new SC01_FH_FlightsHotelsSearchPage(driver);
    }

    @When("user opens flight + hotel tab")
    public void open_tab() {
        searchPage.openFlightHotelTab();
    }

    @And("user clears search fields")
    public void clear_fields() {
        searchPage.resetSearch();
    }

    @And("user clicks search button")
    public void click_search() {
        searchPage.clickSearch();
    }
}