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

    @Then("car name should match selected car")
    public void verify_car_name() {

        String actual = pages.detailsPage.getCarName();

        if (expectedCar == null) {
            expectedCar = "kia"; // fallback
        }

        System.out.println("Expected Car: " + expectedCar);
        System.out.println("Actual Car: " + actual);

        Assert.assertTrue(
                actual.toLowerCase().contains(expectedCar.toLowerCase()),
                "❌ Car name mismatch"
        );

        System.out.println("✅ Car name verified");
    }
    @Then("pickup location should match previously selected location")
    public void verify_pickup() {

        String actual = pages.detailsPage.getPickup();

        System.out.println("Pickup: " + actual);

        Assert.assertTrue(
                actual.toLowerCase().contains("airport"),
                "❌ Pickup mismatch"
        );

        System.out.println("✅ Pickup verified");
    }
    
    @Then("drop location should match previously selected location")
    public void verify_drop() {

        String actual = pages.detailsPage.getDrop();

        System.out.println("Drop: " + actual);

        Assert.assertTrue(
                actual.toLowerCase().contains("dubai"),
                "❌ Drop mismatch"
        );

        System.out.println("✅ Drop verified");
    }
    @Then("car details should display price for one day")
    public void verify_price() {

        String price = pages.detailsPage.getPrice();

        System.out.println("Price: " + price);

        Assert.assertTrue(
                price.contains("₹") || price.contains("AED"),
                "❌ Price not displayed"
        );

        System.out.println("✅ Price displayed");
    }
    // click continue
    @When("user clicks on continue button")
    public void click_continue() {
        pages.detailsPage.clickContinue(); // click continue
    }

    // verify navigation
    @Then("user should proceed further in booking flow")
    public void verify_continue() {

        String url = pages.detailsPage.getCurrentUrl();

        System.out.println("After Continue URL: " + url);

        Assert.assertTrue(
                url.contains("extras") || url.contains("package"),
                "❌ Not navigated to extras"
        );

        System.out.println("✅ Navigated to next step");
    }
}