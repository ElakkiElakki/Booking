package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AT_SearchPage extends baseclass {

    private final WebDriverWait wait;

    private final By destinationField = By.xpath(
            "//input[contains(@placeholder,'Destination') or contains(@placeholder,'Where') or @name='query']" +
            " | //label[contains(.,'Destination')]/following::input[1]"
    );

    private final By searchButton = By.xpath(
            "//button[@data-testid='search-button']" +
            " | //button[@type='submit']" +
            " | //button[contains(.,'Search')]" +
            " | //span[contains(.,'Search')]/ancestor::button"
    );

    public AT_SearchPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openAttractionsPage() {
        driver.get("https://www.booking.com/attractions/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(destinationField));
        System.out.println("Opened Attractions page directly.");
    }

    public void enterDestination(String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationField));
        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.DELETE);
        field.sendKeys(value);
        System.out.println("Destination entered: " + value);
    }

    public void clearDestination() {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationField));
        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.DELETE);
    }

    public void selectSuggestionIfPresent() {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationField));
            Thread.sleep(2000);

            field.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(1000);
            field.sendKeys(Keys.ENTER);
            Thread.sleep(2500);

            String valueAfterSelection = field.getAttribute("value");
            System.out.println("Destination value after suggestion select: " + valueAfterSelection);

            if (valueAfterSelection == null || valueAfterSelection.trim().isEmpty()) {
                throw new RuntimeException("Destination suggestion was not selected properly.");
            }

            System.out.println("Destination suggestion selected by keyboard.");
        } catch (Exception e) {
            throw new RuntimeException("Suggestion was not selected properly.", e);
        }
    }

    public void clickSearch() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            button.click();
        } catch (StaleElementReferenceException e) {
            WebElement freshButton = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            freshButton.click();
        } catch (Exception e) {
            WebElement freshButton = wait.until(ExpectedConditions.presenceOfElementLocated(searchButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", freshButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", freshButton);
        }

        try {
            Thread.sleep(5000);
        } catch (Exception ignored) {
        }

        System.out.println("Current URL after Attractions search: " + driver.getCurrentUrl());
    }

    public boolean isValidationDisplayed() {
        try {
            String currentUrl = driver.getCurrentUrl();

            if (currentUrl.contains("/attractions/searchresults.html")) {
                return false;
            }

            return !driver.findElements(By.xpath(
                    "//*[contains(text(),'Please enter a destination')]" +
                    " | //*[contains(text(),'Please provide a destination')]" +
                    " | //*[contains(text(),'Enter a destination')]"
            )).isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isResultPageDisplayed() {
        try {
            Thread.sleep(3000);

            String currentUrl = driver.getCurrentUrl();

            System.out.println("Attractions result page URL: " + currentUrl);
            System.out.println("Validation visible: " + isValidationDisplayed());

            if (isValidationDisplayed()) {
                return false;
            }

            return currentUrl.contains("/attractions/searchresults.html");

        } catch (Exception e) {
            return false;
        }
    }
}