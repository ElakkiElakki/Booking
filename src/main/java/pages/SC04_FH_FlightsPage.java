package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class SC04_FH_FlightsPage {

    WebDriver driver;
    WebDriverWait wait;

    public SC04_FH_FlightsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ===== EXISTING LOCATORS =====
    private By firstContinueBtn = By.cssSelector("button[data-testid='select-flight-button']");
    private By modalContainer = By.xpath("//div[@role='dialog']");
    private By modalContinueBtn = By.xpath("//div[@role='dialog']//button[@data-testid='lmn-ds-btn']");

    // ===== NEW LOCATORS =====
    private By cheapestTab = By.xpath("//p[text()='Cheapest']");
    private By fastestTab = By.xpath("//p[text()='Fastest']");
    private By bestTab = By.xpath("//p[text()='Best']");
    private By luggageCheckbox = By.xpath("//span[text()='Checked luggage incl.']/following::button[@role='checkbox'][1]");
    private By directRouteSwitch = By.xpath("//span[text()='Show direct routes only']/following::label[@data-testid='switch-wrapper']");
    private By oneStopCheckbox = By.id("ONE");
    private By resetFilters = By.xpath("//a[text()='Reset filters']");

    // ===== VERIFY FLIGHT PAGE =====
    public void verifyFlightPage() {
        System.out.println("⏳ Waiting for flight page...");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("transportDetail"),
                ExpectedConditions.presenceOfElementLocated(firstContinueBtn)
        ));

        System.out.println("✅ Flight page displayed");
    }

    // ===== SORTING METHODS =====

    public void clickCheapest() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(cheapestTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        System.out.println("✅ Cheapest tab clicked");
    }

    public void clickFastest() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(fastestTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        System.out.println("✅ Fastest tab clicked");
    }

    public void clickBest() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(bestTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        System.out.println("✅ Best tab selected");
    }

    // ===== FILTER METHODS =====

    public void clickLuggageFilter() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(luggageCheckbox));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);

        String state = el.getAttribute("aria-checked");
        System.out.println("✅ Luggage filter applied → " + state);
    }

    public void toggleDirectRoutes() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("⏳ Toggling direct routes...");

        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(directRouteSwitch)
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);

        try {
            el.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", el);
        }

        System.out.println("✅ Direct routes toggled");
    }

    public void clickOneStop() {
        try {
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(oneStopCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            System.out.println("✅ 1 Stop selected");
        } catch (Exception e) {
            System.out.println("⚠️ 1 Stop disabled - skipped");
        }
    }

    public void clickResetFilters() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(resetFilters));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        System.out.println("✅ Filters reset");
    }

    // ===== EXISTING MAIN FLOW (UNCHANGED) =====
    public void selectFlightAndContinue() {

        System.out.println("⏳ Waiting for flight results...");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement firstBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(firstContinueBtn)
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", firstBtn);

        wait.until(ExpectedConditions.elementToBeClickable(firstBtn));

        try {
            firstBtn.click();
        } catch (Exception e) {
            Actions actions = new Actions(driver);
            actions.moveToElement(firstBtn).pause(Duration.ofMillis(300)).click().perform();
        }

        System.out.println("✅ Flight first Continue clicked");

        // ===== MODAL =====
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContainer));
        System.out.println("✅ Flight modal opened");

        WebElement modalBtn = wait.until(
                ExpectedConditions.elementToBeClickable(modalContinueBtn)
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", modalBtn);

        try {
            modalBtn.click();
        } catch (Exception e) {
            Actions actions = new Actions(driver);
            actions.moveToElement(modalBtn).pause(Duration.ofMillis(300)).click().perform();
        }

        System.out.println("✅ Flight modal Continue clicked");

        // ===== FINAL NAVIGATION =====
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("review"),
                ExpectedConditions.urlContains("checkout")
        ));

        if (driver.getCurrentUrl().contains("review")) {
            System.out.println("⏳ Waiting for redirect to checkout...");
            wait.until(ExpectedConditions.urlContains("checkout"));
        }

        System.out.println("✅ Final traveller page reached");
    }

    // ===== VERIFY TRAVELLER PAGE =====
    public void verifyTravellerPage() {

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout"),
                ExpectedConditions.urlContains("review")
        ));

        System.out.println("✅ Traveller details page displayed");
    }
}