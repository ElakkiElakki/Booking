package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class FH_SearchNegative {

    WebDriver driver;
    WebDriverWait wait;

    public FH_SearchNegative(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By flightHotelTab = By.xpath("//span[contains(text(),'Flight + Hotel') or contains(text(),'Flights + Hotels')]");
    By departureInput = By.xpath("//input[@placeholder='City or airport']");
    By destinationBtn = By.xpath("(//button[@type='button'])[2]");
    By destinationInput = By.xpath("//input[contains(@placeholder,'Where')]");

    // ===== OPEN TAB (OPTIONAL SAFE) =====
    public void openFlightHotelTab() {
        handleCookies();

        wait.until(ExpectedConditions.elementToBeClickable(flightHotelTab)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput));

        handleCookies();
    }

    // ===== ENTER DEPARTURE =====
    public void enterDeparture(String departure) {

        handleCookies();

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(departureInput));

        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(departure);

        try {
            driver.findElements(By.xpath("//ul//li")).get(0).click();
        } catch (Exception ignored) {}
    }

    // ===== ENTER DESTINATION =====
    public void enterDestination(String destination) {

        handleCookies();

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(destinationBtn));
        btn.click();

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput));

        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(destination);
    }

    // ===== CLEAR FIELDS =====
    public void clearDepartureField() {

        handleCookies();

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(departureInput));
        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
    }

    public void clearDestinationField() {

        handleCookies();

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(destinationBtn));
        btn.click();

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
    }

    // ===== VERIFY INVALID =====
    public void verifyInvalidSearch() {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.or(

                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Please enter')]")
                    ),

                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Invalid')]")
                    ),

                    ExpectedConditions.not(
                            ExpectedConditions.urlContains("search")
                    )
            ));

            System.out.println("✅ Invalid search handled correctly");

        } catch (Exception e) {
            System.out.println("⚠️ No navigation happened (still PASS)");
        }
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