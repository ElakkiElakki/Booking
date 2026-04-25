package stepDefinition;

import io.cucumber.java.en.Given;
import base.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;


public class CR_CommonSteps {

    WebDriver driver;

    // 🔹 RESULTS PAGE
    @Given("user is on car rental results page")
    public void user_is_on_results_page() {


        pages.searchPage.openCarRentalPage(); // open site
        pages.searchPage.enterPickup("Dubai"); // enter pickup
        pages.searchPage.clickCheckbox(); // enable drop
        pages.searchPage.enterDrop("Dubai Downtown"); // enter drop
        pages.searchPage.selectDate(); // select dates
        pages.searchPage.clickSearch(); // click search

        System.out.println("✅ Navigated to Results Page");
    }

    // 🔹 DETAILS PAGE
    @Given("user is on car rental details page")
    public void user_is_on_details_page() {

        user_is_on_results_page(); // reuse search flow

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2"))); // wait for cars

        List<WebElement> cars = driver.findElements(By.xpath("//h2//div")); // car names
        List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(@aria-label,'View deal')]")); // buttons

        for (int i = 0; i < cars.size(); i++) {

            String text = cars.get(i).getText();

            if (text.toLowerCase().contains("kia")) {

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true);", buttons.get(i)); // scroll

                buttons.get(i).click(); // click car

                System.out.println("✅ Selected car: " + text);
                break;
            }
        }

        pages.detailsPage.switchToNewTab(); // switch tab
    }

    // 🔹 EXTRAS PAGE
    @Given("user is on car rental extras page")
    public void user_is_on_extras_page() {

        user_is_on_details_page(); // go till details

        pages.detailsPage.clickContinue(); // go to extras

        System.out.println("✅ Navigated to Extras Page");
    }

    // 🔹 PROTECTION PAGE
    @Given("user is on car rental protection page")
    public void user_is_on_protection_page() {

        user_is_on_extras_page(); // go till extras

        pages.extrasPage.clickContinue(); // go to protection

        System.out.println("✅ Navigated to Protection Page");
    }

    // 🔹 BOOKING PAGE
    @Given("user is on booking page")
    public void user_is_on_booking_page() {

        user_is_on_protection_page(); // go till protection

        pages.protectionPage.clickWithProtection(); // go to booking

        System.out.println("✅ Navigated to Booking Page");
    }
}