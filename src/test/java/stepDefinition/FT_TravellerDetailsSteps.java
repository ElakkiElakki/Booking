package stepDefinition;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.*;
import pages.FT_TravellerDetailsPage;
import util.AllFunctionalities;

public class FT_TravellerDetailsSteps {

    WebDriver driver = AllFunctionalities.getDriver();
    FT_TravellerDetailsPage page = new FT_TravellerDetailsPage(driver);

    // ================= ADULT =================

    @When("user completes adult validation flow")
    public void adult_flow() {
        page.enterAdultFullFlow();
    }

    // ================= CHILD =================

    @When("user enters valid child details")
    public void child_flow() {
        page.enterValidChild();
    }

    // ================= CONTACT =================

    @When("user validates email flow")
    public void email_flow() {
        page.enterEmailFlow();
    }

    @When("user enters phone and proceeds")
    public void phone_and_next() {
        page.enterPhone();
        page.clickNext();
    }

    // ================= VALIDATIONS =================

    @Then("user should see traveller validation error")
    public void traveller_error() {
        page.verifyTravellerError();
    }

    @Then("user should see email error")
    public void email_error() {
        page.verifyEmailError();
    }
}