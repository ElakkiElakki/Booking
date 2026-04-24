package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class SC02_FH_HotelListingPage {

    WebDriver driver;
    WebDriverWait wait;

    public SC02_FH_HotelListingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ✅ First hotel
    private By firstHotelLink = By.xpath("(//a[@data-testid='details'])[1]");

    // ✅ CORRECT locator (based on your HTML)
    private By continueBtn = By.xpath("//button[@data-testid='continue-button']");

    // ================= NEW METHODS =================

    public void toggleMapDesktop() throws InterruptedException {

        // Hide Map
        By hideMap = By.xpath("//button[text()='Hide map']");
        WebElement hideBtn = wait.until(ExpectedConditions.elementToBeClickable(hideMap));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", hideBtn);
        hideBtn.click();
        System.out.println("✅ Clicked Hide Map");

        Thread.sleep(2000);

        // Show Map
        By showMap = By.xpath("//button[text()='Show map']");
        WebElement showBtn = wait.until(ExpectedConditions.elementToBeClickable(showMap));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", showBtn);
        showBtn.click();
        System.out.println("✅ Clicked Show Map");

        Thread.sleep(2000);
    }

    public void applyFiveStarFilter() throws InterruptedException {

        // Open Stars dropdown
        By starsBtn = By.id("Pill-StarsContainer");
        WebElement stars = wait.until(ExpectedConditions.elementToBeClickable(starsBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", stars);
        stars.click();
        System.out.println("✅ Stars dropdown opened");

        // Select 5 stars
        By fiveStar = By.id("exp_elem_hotel_stars_5");
        WebElement starOption = wait.until(ExpectedConditions.elementToBeClickable(fiveStar));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", starOption);
        System.out.println("✅ 5 Stars selected");

        // Click Apply
        By applyBtn = By.xpath("//button[text()='Apply']");
        WebElement apply = wait.until(ExpectedConditions.elementToBeClickable(applyBtn));

        apply.click();
        System.out.println("✅ Applied 5 star filter");

        Thread.sleep(3000);
    }

    public void sortByLowestPrice() throws InterruptedException {

        // Open Sort dropdown
        By sortBtn = By.id("Pill-SortContainer");
        WebElement sort = wait.until(ExpectedConditions.elementToBeClickable(sortBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", sort);
        sort.click();
        System.out.println("✅ Sort dropdown opened");

        // Select Price lowest
        By lowestPrice = By.id("label_sort_price_asc");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(lowestPrice));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        System.out.println("✅ Selected Price (lowest first)");

        Thread.sleep(3000);
    }

    // ================= EXISTING =================

    public void selectFirstHotel() {

        System.out.println("⏳ Waiting for hotel list...");

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[@data-testid='details']")
        ));

        WebElement hotelLink = wait.until(
            ExpectedConditions.elementToBeClickable(firstHotelLink)
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", hotelLink
        );

        try { Thread.sleep(1000); } catch (Exception ignored) {}

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", hotelLink
        );

        System.out.println("✅ First hotel clicked");

        // ✅ WAIT FOR NEW TAB
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        String currentWindow = driver.getWindowHandle();

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void verifyRoomPage() {

        System.out.println("⏳ Waiting for room page...");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("hotel"),
                ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        ));

        wait.until(ExpectedConditions.visibilityOfElementLocated(continueBtn));

        System.out.println("✅ Room page displayed");
    }
}