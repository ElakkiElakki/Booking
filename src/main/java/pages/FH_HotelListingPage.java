package pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class FH_HotelListingPage {

    WebDriver driver;
    WebDriverWait wait;

    public FH_HotelListingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ✅ First hotel
    private By firstHotelLink = By.xpath("(//a[@data-testid='details'])[1]");

    // ================= MAP =================
    public void toggleMapDesktop() throws InterruptedException {

        By hideMap = By.xpath("//button[text()='Hide map']");
        WebElement hideBtn = wait.until(ExpectedConditions.elementToBeClickable(hideMap));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", hideBtn);
        hideBtn.click();
        System.out.println("✅ Clicked Hide Map");

        Thread.sleep(2000);

        By showMap = By.xpath("//button[text()='Show map']");
        WebElement showBtn = wait.until(ExpectedConditions.elementToBeClickable(showMap));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", showBtn);
        showBtn.click();
        System.out.println("✅ Clicked Show Map");

        Thread.sleep(2000);
    }

    // ================= FILTER =================
    public void applyFiveStarFilter() throws InterruptedException {

        By starsBtn = By.id("Pill-StarsContainer");
        WebElement stars = wait.until(ExpectedConditions.elementToBeClickable(starsBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", stars);
        stars.click();
        System.out.println("✅ Stars dropdown opened");

        By fiveStar = By.id("exp_elem_hotel_stars_5");
        WebElement starOption = wait.until(ExpectedConditions.elementToBeClickable(fiveStar));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", starOption);
        System.out.println("✅ 5 Stars selected");

        By applyBtn = By.xpath("//button[text()='Apply']");
        WebElement apply = wait.until(ExpectedConditions.elementToBeClickable(applyBtn));

        apply.click();
        System.out.println("✅ Applied 5 star filter");

        Thread.sleep(3000);
    }

    // ================= SORT =================
    public void sortByLowestPrice() throws InterruptedException {

        By sortBtn = By.id("Pill-SortContainer");
        WebElement sort = wait.until(ExpectedConditions.elementToBeClickable(sortBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", sort);
        sort.click();
        System.out.println("✅ Sort dropdown opened");

        By lowestPrice = By.id("label_sort_price_asc");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(lowestPrice));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        System.out.println("✅ Selected Price (lowest first)");

        Thread.sleep(3000);
    }

    // ================= SELECT HOTEL =================
    public void selectFirstHotel() {

        System.out.println("⏳ Waiting for hotel list...");

        wait.until(driver ->
            driver.findElements(By.xpath("//a[@data-testid='details']")).size() > 3
        );

        WebElement hotel = wait.until(
            ExpectedConditions.elementToBeClickable(firstHotelLink)
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", hotel
        );

        // 🔥 STORE OLD WINDOWS (IMPORTANT)
        Set<String> oldWindows = driver.getWindowHandles();

        System.out.println("👉 Before click URL: " + driver.getCurrentUrl());

        // CLICK
        try {
            hotel.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hotel);
        }

        System.out.println("✅ First hotel clicked");

        // 🔥 WAIT FOR NEW TAB OR SAME TAB NAVIGATION
        try {
            // wait for new tab
            wait.until(ExpectedConditions.numberOfWindowsToBe(oldWindows.size() + 1));

            // get new tab
            Set<String> newWindows = driver.getWindowHandles();
            newWindows.removeAll(oldWindows);

            String newTab = newWindows.iterator().next();
            driver.switchTo().window(newTab);

            System.out.println("✅ Switched to correct new tab");

        } catch (TimeoutException e) {
            // fallback: same tab navigation
            System.out.println("⚠️ No new tab, checking same tab navigation...");
        }

        // 🔥 FINAL VALIDATION (WORKS FOR BOTH CASES)
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("hotelDetail"),
            ExpectedConditions.urlContains("pageType=hotelDetail")
        ));

        System.out.println("👉 After click URL: " + driver.getCurrentUrl());
    }
    // ================= VERIFY ROOM PAGE =================
    public void verifyRoomPage() {

        System.out.println("⏳ Waiting for room page...");

        wait.until(ExpectedConditions.and(
            ExpectedConditions.urlContains("pageType=hotelDetail"),
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button[data-testid='continue-button']")
            )
        ));

        System.out.println("✅ Room page displayed");
    }
}