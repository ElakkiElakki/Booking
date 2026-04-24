package stepDefinition;

import io.cucumber.java.en.*;
import org.testng.Assert;
import base.pages;

public class CR_DetailsSteps {

    String expectedCar; // store expected car

    // select car
    @When("user selects {string} car from results")
    public void select_car(String carName) {
        expectedCar = carName; // store expected value
        System.out.println("Selecting car: " + carName);
    }

    // switch to new tab
    @Then("car details page should open in a new tab")
    public void verify_new_tab() {
        pages.detailsPage.switchToNewTab(); // switch tab
    }

    // verify car name
    @Then("car name should match selected car")
    public void verify_car_name() {
        String actual = pages.detailsPage.getCarName(); // get actual name
        if (expectedCar == null) {
            expectedCar = "kia";
        }    }

    // click continue
    @When("user clicks on continue button")
    public void click_continue() {
        pages.detailsPage.clickContinue(); // click continue
    }

    // verify navigation
    @Then("user should proceed further in booking flow")
    public void verify_continue() {
    	String url = pages.detailsPage.getCurrentUrl();

    	Assert.assertTrue(url.contains("extras") || url.contains("package"),
    	        "❌ Not navigated to extras");
    }
}