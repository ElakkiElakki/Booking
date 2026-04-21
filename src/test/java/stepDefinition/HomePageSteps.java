package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HomePage;
import util.AllFunctionalities;

public class HomePageSteps {

    HomePage homePage;

    @Given("user launches the Booking application")
    public void user_launches_the_booking_application() {
        homePage = new HomePage(AllFunctionalities.getDriver());
        homePage.openApplication();
    }

    @Then("Booking homepage should be displayed successfully")
    public void booking_homepage_should_be_displayed_successfully() {
        boolean result = homePage.isHomePageDisplayed();
        System.out.println("Homepage displayed status: " + result);
        Assert.assertTrue(result, "Booking homepage is not displayed");
    }
}