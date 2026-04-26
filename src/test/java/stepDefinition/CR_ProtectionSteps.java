package stepDefinition;

import io.cucumber.java.en.*;
import util.AllFunctionalities;

import org.testng.Assert;
import base.pages;


public class CR_ProtectionSteps {

	@When("user clicks go to book with full protection")
	public void click_with_protection() {
	    pages.protectionPage.clickWithProtection();
	}

	@When("user clicks go to book without full protection")
	public void click_without_protection() {
	    pages.protectionPage.clickWithoutProtection();
	}
    // verify booking page
    @Then("user should proceed to booking page")
    public void verify_navigation() {
//        String url = pages.protectionPage.driver.getCurrentUrl(); 
    	String url = AllFunctionalities.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("book"), "❌ Not navigated to booking page");
    }
}