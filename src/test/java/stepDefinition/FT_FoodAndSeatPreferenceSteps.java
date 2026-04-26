package stepDefinition;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.*;
import pages.FT_FoodAndSeatPreferencePages;
import util.AllFunctionalities;

public class FT_FoodAndSeatPreferenceSteps {

    WebDriver driver = AllFunctionalities.getDriver();
    FT_FoodAndSeatPreferencePages page = new FT_FoodAndSeatPreferencePages(driver);

    @When("user selects food preference for all travellers")
    public void select_food() {
        page.selectFood();
    }

    @When("user expands price breakdown dropdown")
    public void expand_price() {
        page.expandPriceDropdown();
    }

    @Then("user lands on flexibility page")
    public void flex_page() {
        page.waitForFlexPage();
    }

    @When("user selects flexible ticket option")
    public void select_flex() {
        page.selectTravelProtectionIfPresent();
        page.selectFlexibleOption();
    }

    @When("user clicks next")
    public void click_next() {
        page.clickNext();
    }

    @When("user selects two flight seats")
    public void select_two_flight_seats() {
        page.selectSeatsForBothSegments();
    }

    @When("user proceeds to flight payment")
    public void proceed_flight_payment() {
        page.clickCheckOrSkip();
    }

    @Then("user should land on payment page")
    public void verify_payment_page() {
        page.verifyPaymentPage();
    }
}