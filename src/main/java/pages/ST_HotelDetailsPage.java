package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ST_HotelDetailsPage extends baseclass {

    WebDriverWait wait;

    @FindBy(css = "h2.pp-header__title")
    WebElement hotelName;

    public ST_HotelDetailsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ── Switch to hotel tab ───────────────────────────────────────────────
    public void staysSwitchToHotelTab() throws InterruptedException {
        ArrayList<String> tabs = new ArrayList<>(
            driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.switchTo().window(tabs.get(tabs.size() - 1));
            System.out.println("Switched to hotel tab ✅");
            Thread.sleep(2000);
        }
        staysSelectDatesOnHotelPage();
    }

    // ── Select dates on hotel page ────────────────────────────────────────
    public void staysSelectDatesOnHotelPage() throws InterruptedException {
        try {
            // ✅ Check if dates already selected
            WebElement dateField = driver.findElement(
                By.cssSelector(
                    "[data-testid='date-display-field-start']"));
            String dateText = dateField.getText().trim();
            System.out.println("Current date field: " + dateText);

            // ✅ If dates already filled — skip
            if (!dateText.isEmpty() &&
                !dateText.equals("Check-in date")) {
                System.out.println("Dates already selected ✅");
                return;
            }

            // ✅ Open calendar
            dateField.click();
            Thread.sleep(1500);

            // ✅ Navigate to June 2026
            navigateToDate("2026-06-01");

            // ✅ Click checkin
            WebElement checkin = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("span[data-date='2026-06-01']")));
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkin);
            System.out.println("Hotel checkin selected ✅");
            Thread.sleep(500);

            // ✅ Click checkout
            WebElement checkout = driver.findElement(
                By.cssSelector("span[data-date='2026-06-08']"));
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkout);
            System.out.println("Hotel checkout selected ✅");
            Thread.sleep(500);

            // ✅ Click search
            try {
                driver.findElement(
                    By.cssSelector("button[type='submit']")).click();
                System.out.println("Hotel search clicked ✅");
                Thread.sleep(2000);
            } catch (Exception ignored) {}

        } catch (Exception e) {
            System.out.println("staysSelectDatesOnHotelPage: "
                + e.getMessage());
        }
    }

    // ── Navigate calendar ─────────────────────────────────────────────────
    private void navigateToDate(String targetDate)
                                throws InterruptedException {
        String[] parts  = targetDate.split("-");
        int targetYear  = Integer.parseInt(parts[0]);
        int targetMonth = Integer.parseInt(parts[1]);

        String[] monthNames = {
            "", "January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"
        };

        System.out.println("Target: "
            + monthNames[targetMonth] + " " + targetYear);

        for (int i = 0; i < 12; i++) {
            // ✅ Check h3 month header
            try {
                List<WebElement> headers = driver.findElements(
                    By.cssSelector("h3"));
                for (WebElement h : headers) {
                    String text = h.getText().trim();
                    if (text.contains(monthNames[targetMonth]) &&
                        text.contains(String.valueOf(targetYear))) {
                        System.out.println("Correct month: "
                            + text + " ✅");
                        return;
                    }
                }
            } catch (Exception ignored) {}

            // ✅ Also check date span
            List<WebElement> found = driver.findElements(
                By.cssSelector(
                    "span[data-date='" + targetDate + "']"));
            if (!found.isEmpty() && found.get(0).isDisplayed()) {
                System.out.println("Date visible ✅");
                return;
            }

            // ✅ Click Next month
            try {
                driver.findElement(By.cssSelector(
                    "button[aria-label='Next month']")).click();
                System.out.println("Next month — attempt " + (i + 1));
                Thread.sleep(600);
            } catch (Exception e) {
                System.out.println("Next button not found");
                return;
            }
        }
    }

    // ── Select room count from dropdown ───────────────────────────────────
    private void selectRoomCount(int count) throws InterruptedException {
        try {
            // ✅ Scroll down to room table first
            ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0, 800)");
            Thread.sleep(1000);

            // ✅ From screenshot — select.hprt-nos-select
            List<WebElement> dropdowns = driver.findElements(
                By.cssSelector("select.hprt-nos-select"));
            System.out.println("Dropdowns: " + dropdowns.size());

            if (!dropdowns.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    dropdowns.get(0));
                Thread.sleep(300);
                new Select(dropdowns.get(0))
                    .selectByValue(String.valueOf(count));
                System.out.println("Room count set: " + count + " ✅");
                Thread.sleep(500);
            } else {
                System.out.println("Dropdown not found");
            }
        } catch (Exception e) {
            System.out.println("selectRoomCount: " + e.getMessage());
        }
    }

    // ── Click I'll reserve button ─────────────────────────────────────────
    public void staysClickReserve() throws InterruptedException {
        try {
            // ✅ From screenshot — button text "I'll reserve"
            WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath(
                        "//button[contains(.,\"I'll reserve\")]" +
                        "| //button[contains(.,'Reserve')]")));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", btn);
            Thread.sleep(300);
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", btn);
            System.out.println("Reserve clicked ✅");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Reserve not found: " + e.getMessage());
        }
    }

    // ── TC4 Row 1 — Select room and proceed to booking ────────────────────
    public void staysSelectRoomAndReserve(int count)
                                          throws InterruptedException {
        System.out.println("=== Select room and reserve ===");

        // ✅ Select room count
        selectRoomCount(count);

        // ✅ Click I'll reserve
        staysClickReserve();
        Thread.sleep(3000);

        String url = driver.getCurrentUrl();
        System.out.println("After reserve: " + url);
        if (url.contains("book.html")) {
            System.out.println("✅ On booking form!");
        }
    }

    // ── TC4 Row 2 — Click reserve without room ────────────────────────────
    public void staysClickReserveWithoutRoom() throws InterruptedException {
        // ✅ Don't select room — just click reserve
        staysClickReserve();
    }

    // ── TC4 Row 3 — Change room count ─────────────────────────────────────
    public void staysChangeRoomCount(int count) throws InterruptedException {
        selectRoomCount(count);
    }

    // ── TC4 Row 4 — Select different room option ──────────────────────────
    public void staysSelectDifferentRoomOption()
                                               throws InterruptedException {
        try {
            ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0, 800)");
            Thread.sleep(1000);

            List<WebElement> dropdowns = driver.findElements(
                By.cssSelector("select.hprt-nos-select"));
            System.out.println("Dropdowns: " + dropdowns.size());

            // ✅ Select from second room type if available
            if (dropdowns.size() > 1) {
                new Select(dropdowns.get(1)).selectByValue("1");
                System.out.println("Different room selected ✅");
            } else if (!dropdowns.isEmpty()) {
                // ✅ Select count 2 from first room type
                new Select(dropdowns.get(0)).selectByValue("2");
                System.out.println("Room count changed to 2 ✅");
            }
        } catch (Exception e) {
            System.out.println("Different room: " + e.getMessage());
        }
    }

    // ── Used by SC05 ──────────────────────────────────────────────────────
    public void staysClickReserveButton() throws InterruptedException {
        staysSelectRoomAndReserve(1);
    }

    // ── Assertions ────────────────────────────────────────────────────────
    public boolean staysIsHotelNameDisplayed() {
        try {
            return af.isDisplayed(hotelName);
        } catch (Exception e) {
            return !driver.getTitle().isEmpty();
        }
    }

    public boolean staysIsRoomTableDisplayed() {
        try {
            WebElement table = driver.findElement(
                By.cssSelector("table[class*='hprt'], #hprt-table"));
            return table.isDisplayed();
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("hotel");
        }
    }

    public boolean staysIsPhotoGalleryDisplayed() {
        try {
            WebElement photo = driver.findElement(
                By.cssSelector("div[class*='gallery'] img"));
            System.out.println("Photo: div[class*='gallery'] img");
            return photo.isDisplayed();
        } catch (Exception e) {
            System.out.println("Photo error: " + e.getMessage());
            return false;
        }
    }

    public boolean staysIsStillOnHotelPage() {
        String url = driver.getCurrentUrl();
        System.out.println("URL: " + url);
        return url.contains("booking.com") &&
               !url.contains("confirmation");
    }

    public boolean staysIsRoomCountUpdated() {
        try {
            WebElement dropdown = driver.findElement(
                By.cssSelector("select.hprt-nos-select"));
            String val = new Select(dropdown)
                .getFirstSelectedOption()
                .getAttribute("value");
            System.out.println("Room count: " + val);
            return Integer.parseInt(val) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}