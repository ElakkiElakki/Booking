package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class FH_FlightsHotelsSearchPage {

    WebDriver driver;
    WebDriverWait wait;

    public FH_FlightsHotelsSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== LOCATORS =====
    By flightHotelTab = By.xpath("//span[contains(text(),'Flight + Hotel') or contains(text(),'Flights + Hotels')]");
    By departureInput = By.xpath("//input[@placeholder='City or airport']");
    By destinationBtn = By.xpath("(//button[@type='button'])[2]");
    By destinationInput = By.xpath(
    	    "//input[contains(@placeholder,'Where') or contains(@placeholder,'Destination') or contains(@aria-label,'Where') or contains(@aria-label,'Destination')]"
    	);    By searchBtn = By.xpath("//button[@type='submit']");

    // ===== OPEN TAB =====
    public void openFlightHotelTab() {

        handleCookies();

        wait.until(ExpectedConditions.elementToBeClickable(flightHotelTab)).click();

        driver.get("https://www.booking.com/packages.html");

        wait.until(d ->
                d.findElements(By.xpath("//input")).size() > 0 ||
                d.findElements(By.xpath("//button[contains(.,'Where') or contains(.,'Destination')]")).size() > 0
        );

        System.out.println("✅ Flight + Hotel page ready");

        handleCookies();
    }

    // ===== ENTER DEPARTURE =====
    public void enterDeparture(String departure) {

        if (departure == null || departure.trim().isEmpty()) return;

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(departureInput));

        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(departure);

        By suggestion = By.xpath("//ul//li//span");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));
            driver.findElements(suggestion).get(0).click();
            System.out.println("✅ Departure: " + departure);
        } catch (Exception e) {
            System.out.println("⚠️ No suggestion for departure");
        }
    }

    // ===== ENTER DESTINATION =====
    public void enterDestination(String destination) {

        if (destination == null || destination.trim().isEmpty()) return;

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(destinationBtn));
        btn.click();

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput));

        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(destination);

        By suggestions = By.xpath("//ul//li");

        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(suggestions, 0));

            List<WebElement> list = driver.findElements(suggestions);

            for (WebElement el : list) {
                if (el.getText().toLowerCase().contains(destination.toLowerCase())) {
                    el.click();
                    System.out.println("✅ Destination: " + destination);
                    return;
                }
            }

        } catch (Exception e) {
            System.out.println("⚠️ No suggestion for destination");
        }
    }
 // ===== RESET SEARCH =====
    public void resetSearch() {

        try {
            // Clear departure
            WebElement dep = wait.until(ExpectedConditions.elementToBeClickable(departureInput));
            dep.click();
            dep.sendKeys(Keys.CONTROL + "a");
            dep.sendKeys(Keys.DELETE);

            // Clear destination
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(destinationBtn));
            btn.click();

            WebElement dest = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput));
            dest.sendKeys(Keys.CONTROL + "a");
            dest.sendKeys(Keys.DELETE);

            System.out.println("🔄 Search fields cleared");

        } catch (Exception e) {
            System.out.println("⚠️ Unable to reset fields");
        }
    }
    // ===== CLICK SEARCH =====
    public void clickSearch() {

        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", search);

        search.click();

        System.out.println("✅ Search clicked");
    }

    // ===== VERIFY RESULTS =====
    public void verifyResults() {

        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(40));

        longWait.until(ExpectedConditions.urlContains("search"));

        longWait.until(driver ->
                driver.findElements(By.xpath("//h4[contains(@id,'card-title')]")).size() > 2
        );

        System.out.println("✅ Hotel results loaded");
    }

    // ===== HANDLE COOKIES =====
    public void handleCookies() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                shortWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                        By.xpath("//iframe[contains(@id,'sp_message_iframe')]")
                ));
            } catch (Exception ignored) {}

            WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Accept') or contains(.,'Agree')]")
            ));

            btn.click();
            driver.switchTo().defaultContent();

            System.out.println("✅ Cookies handled");

        } catch (Exception e) {
            System.out.println("⚠️ No cookie popup");
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored) {}
        }
    }
}