package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class FH_HotelDetailsPage {

    WebDriver driver;
    WebDriverWait wait;

    public FH_HotelDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ===== LOCATORS =====
    private By firstContinueBtn = By.cssSelector("button[data-testid='continue-button']");
    private By modalContainer = By.xpath("//div[@role='dialog']");
    private By modalContinueBtn = By.xpath(
            "//div[@role='dialog']//div[contains(@class,'BottomContinuePanel')]//button"
    );

    // ===== VERIFY PAGE =====
    public void verifyRoomPage() {
        System.out.println("⏳ Waiting for hotel details page...");
        wait.until(ExpectedConditions.urlContains("hotel"));
        System.out.println("✅ Hotel details page displayed");
    }
    public void verifyFlightPage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.urlContains("transportDetail&destination"));

        System.out.println("✅ Flight selection page displayed");
    }

    // ===== STEP 1: CLICK FIRST CONTINUE =====
    public void selectRoomAndContinue() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("⏳ Clicking first Continue...");

        WebElement firstBtn = wait.until(
                ExpectedConditions.elementToBeClickable(firstContinueBtn)
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", firstBtn);
        js.executeScript("arguments[0].click();", firstBtn);

        System.out.println("✅ First Continue clicked");

        // 🔥 WAIT MODAL FULLY
        waitForRoomModal();

        // 🔥 CLICK SECOND BUTTON
        clickFinalContinue();
    }

    // ===== WAIT MODAL =====
    public void waitForRoomModal() {

        System.out.println("⏳ Waiting for modal...");

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContainer));

        // 🔥 IMPORTANT: allow animation to complete
        try { Thread.sleep(1500); } catch (Exception ignored) {}

        System.out.println("✅ Modal fully loaded");
    }

    // ===== STEP 2: FINAL CONTINUE =====
    public void clickFinalContinue() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("⏳ Waiting for modal Continue button...");

        WebElement btn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(modalContinueBtn)
        );

        wait.until(ExpectedConditions.elementToBeClickable(btn));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        try {
            btn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", btn);
        }

        System.out.println("✅ REAL modal Continue clicked");

        // 🔥 STRONG VALIDATION
        wait.until(ExpectedConditions.and(
        	    ExpectedConditions.urlContains("transportDetail"),
        	    ExpectedConditions.visibilityOfElementLocated(
        	        By.cssSelector("button[data-testid='select-flight-button']")
        	    )
        	));

        System.out.println("✅ Navigation confirmed");
    }
 // ===== NEW LOCATORS =====
    private By viewMapBtn = By.xpath("//button[contains(text(),'View map')]");
    private By closeMapBtn = By.xpath("//button[contains(@class,'CloseIconContainer')]");
    private By reviewsLink = By.xpath("//span[contains(text(),'reviews')]");
    private By facilitiesLink = By.xpath("//a[contains(text(),'View all facilities')]");
    
 // ===== MAP FLOW =====
    public void handleMapFlow() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("⏳ Clicking View Map...");

        WebElement mapBtn = wait.until(ExpectedConditions.elementToBeClickable(viewMapBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", mapBtn);
        js.executeScript("arguments[0].click();", mapBtn);

        System.out.println("✅ Map opened");

        // wait for map overlay
        try { Thread.sleep(3000); } catch (Exception ignored) {}

        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(closeMapBtn));
        js.executeScript("arguments[0].click();", closeBtn);

        System.out.println("✅ Map closed");
    }


    // ===== REVIEWS FLOW =====
    public void handleReviewsFlow() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("⏳ Clicking Reviews...");

        WebElement review = wait.until(ExpectedConditions.elementToBeClickable(reviewsLink));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", review);
        js.executeScript("arguments[0].click();", review);

        System.out.println("✅ Navigated to reviews");

        try { Thread.sleep(3000); } catch (Exception ignored) {}

        // 🔥 scroll back to top
        js.executeScript("window.scrollTo(0,0)");

        System.out.println("✅ Returned to top from reviews");
    }


    // ===== FACILITIES FLOW =====
    public void handleFacilitiesFlow() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("⏳ Clicking Facilities...");

        WebElement facility = wait.until(ExpectedConditions.elementToBeClickable(facilitiesLink));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", facility);
        js.executeScript("arguments[0].click();", facility);

        System.out.println("✅ Navigated to facilities");

        try { Thread.sleep(3000); } catch (Exception ignored) {}

        // 🔥 scroll back to top
        js.executeScript("window.scrollTo(0,0)");

        System.out.println("✅ Returned to top from facilities");
    }
}