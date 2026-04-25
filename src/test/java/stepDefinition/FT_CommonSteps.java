//package stepDefinition;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.When;
//import util.AllFunctionalities;
//
//public class FT_CommonSteps {
//	WebDriver driver = AllFunctionalities.getDriver();
//
//	@Given("user launches booking homepage for flights")
//	public void launch_homepage() {
//		AllFunctionalities.getDriver();
//	}
//
//	@When("user opens flights section")
//	public void open_flights_section() {
//
//		WebDriver driver = AllFunctionalities.getDriver();
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//		WebElement flights = wait.until(ExpectedConditions
//				.elementToBeClickable(By.xpath("//a[@data-testid='header-flights'] | //a[@id='flights']")));
//
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", flights);
//	}
//
//	By nextBtn = By.xpath("//button[.//span[text()='Next']]");
//}






package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import util.AllFunctionalities;

public class FT_CommonSteps {

    WebDriver driver = AllFunctionalities.getDriver();

    @Given("user launches booking homepage for flights")
    public void launch_homepage() {
        driver = AllFunctionalities.getDriver();
        driver.get("https://www.booking.com/");
    }

    @When("user opens flights section")
    public void open_flights_section() {
        driver = AllFunctionalities.getDriver();
        driver.get("https://www.booking.com/flights/");
        System.out.println("Flights page opened: " + driver.getCurrentUrl());
    }

    @Given("user is on booking homepage")
    public void user_is_on_booking_homepage() {
        driver = AllFunctionalities.getDriver();
        driver.get("https://www.booking.com/flights/");
        System.out.println("Flights homepage opened: " + driver.getCurrentUrl());
    }
}