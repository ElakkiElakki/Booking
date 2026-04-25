package stepDefinition;

import io.cucumber.java.en.*;
import util.AllFunctionalities;

import org.testng.Assert;
import base.pages;


public class CR_ProtectionSteps {

    // select protection
    @When("user selects protection {string}")
    public void select_protection(String type) {

        if (type.equalsIgnoreCase("Full Protection")) {
            pages.protectionPage.clickWithProtection(); // select full protection
        } else {
            pages.protectionPage.clickWithoutProtection(); // select without protection
        }
    }

    // verify booking page
    @Then("user should proceed to booking page")
    public void verify_navigation() {
//        String url = pages.protectionPage.driver.getCurrentUrl(); 
    	String url = AllFunctionalities.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("book"), "❌ Not navigated to booking page");
    }
}