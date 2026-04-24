package stepDefinition;

import io.cucumber.java.en.*;
import org.testng.Assert;
import base.pages;


public class CR_ExtrasSteps {

    double basePrice = 0; // base price
    double gpsPrice = 0; // gps price
    double finalPrice; // final price

    // verify extras page
    @Then("user should see available extras")
    public void verify_extras_displayed() {
        Assert.assertTrue(pages.extrasPage.isExtrasPageDisplayed(), "❌ Extras page not loaded");
    }

    // select extra
    @When("user selects {string}")
    public void user_selects_extra(String extra) {

        if (basePrice == 0) {
            basePrice = pages.extrasPage.getBasePrice(); // get base price
        }

        if (extra.equalsIgnoreCase("GPS")) {
            pages.extrasPage.selectGPS(); // select gps
            gpsPrice = pages.extrasPage.getExtraPrice("GPS"); // get gps price
        }
    }

    // validate price update
    @Then("check whether it reflects over the price")
    public void validate_price() {

        finalPrice = pages.extrasPage.getFinalPrice(); // get final price
        double expected = basePrice + gpsPrice; // calculate expected

        Assert.assertTrue(Math.abs(finalPrice - expected) < 5, "❌ Price mismatch");
    }

    // click continue
    @When("user clicks continue from extras page")
    public void click_continue() {
        pages.extrasPage.clickContinue(); // click continue
    }

    // verify protection page
    @Then("user should be on protection page")
    public void verify_protection_page() {
    	String url = pages.extrasPage.getCurrentUrl();
    	Assert.assertTrue(url.contains("package"), "❌ Not on protection page");
    }
}