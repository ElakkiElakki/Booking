package stepDefinition;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.ST_FiltersPage;
import pages.ST_HomePage;
import util.AllFunctionalities;
import java.time.Duration;
import java.util.List;

public class ST_FiltersSteps {

    private final ST_HomePage homePage;
    private final ST_FiltersPage filtersPage;

    public ST_FiltersSteps() {
        this.homePage    = new ST_HomePage(AllFunctionalities.getDriver());
        this.filtersPage = new ST_FiltersPage(AllFunctionalities.getDriver());
    }

    // ── Search with dates ─────────────────────────────────────────────────
    @Given("User has searched for stays in {string} checkin {string} checkout {string}")
    public void userHasSearchedForStaysWithDates(String destination,
                                                  String checkin,
                                                  String checkout)
                                                  throws InterruptedException {
        WebDriver driver = AllFunctionalities.getDriver();
        WebDriverWait localWait = new WebDriverWait(driver,
            Duration.ofSeconds(15));

        // ✅ Step 1 — Dismiss overlay
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var el = document.querySelector('.bbe73dce14');" +
                "if(el) el.remove();");
        } catch (Exception ignored) {}

        try {
            WebElement dismiss = driver.findElement(By.cssSelector(
                "button[aria-label='Dismiss sign in information.']," +
                "button[aria-label='Dismiss sign-in information.']"));
            dismiss.click();
            Thread.sleep(300);
        } catch (Exception ignored) {}

        // ✅ Step 2 — Type destination
        WebElement destField = driver.findElement(By.name("ss"));
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", destField);
        Thread.sleep(300);
        destField.sendKeys(Keys.CONTROL + "a");
        destField.sendKeys(Keys.DELETE);
        destField.clear();
        Thread.sleep(200);
        for (char c : destination.toCharArray()) {
            destField.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
        Thread.sleep(2000);

        // ✅ Step 3 — Click autocomplete suggestion
        try {
            WebElement suggestion = localWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(
                        "[data-testid='autocomplete-result']")));
            System.out.println("Suggestion: " + suggestion.getText());
            suggestion.click();
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("No autocomplete");
        }

        // ✅ Step 4 — Open calendar
        try {
            WebElement dateStart = localWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector(
                        "[data-testid='date-display-field-start']")));
            dateStart.click();
            System.out.println("Date field clicked ✅");
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("Date open failed: " + e.getMessage());
        }

        // ✅ Step 5 — Navigate to checkin month
        navigateCalendarToDate(driver, checkin);

        // ✅ Step 6 — Click checkin date
        try {
            WebElement checkinCell = localWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector(
                        "span[data-date='" + checkin + "']")));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                checkinCell);
            Thread.sleep(300);
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkinCell);
            System.out.println("Checkin " + checkin + " ✅");
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("Checkin failed: " + e.getMessage());
        }

        // ✅ Step 7 — Navigate to checkout month if different
        navigateCalendarToDate(driver, checkout);

        // ✅ Step 8 — Click checkout date
        try {
            WebElement checkoutCell = localWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector(
                        "span[data-date='" + checkout + "']")));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                checkoutCell);
            Thread.sleep(300);
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkoutCell);
            System.out.println("Checkout " + checkout + " ✅");
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }

        // ✅ Step 9 — Click search
        try {
            WebElement searchBtn = driver.findElement(
                By.cssSelector("button[type='submit']"));
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", searchBtn);
            System.out.println("Search clicked ✅");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Search failed: " + e.getMessage());
        }

        // ✅ Step 10 — Verify results
        filtersPage.staysClickViewFullList();
        Assert.assertTrue(
            filtersPage.staysIsResultsDisplayed(),
            "Results page not loaded");
        System.out.println("✅ Search done for: " + destination);
    }

    // ── Search without dates ──────────────────────────────────────────────
    @Given("User has searched for stays in {string}")
    public void userHasSearchedForStays(String destination)
                                        throws InterruptedException {
        userHasSearchedForStaysWithDates(
            destination, "2026-04-25", "2026-05-02");
    }

    // ── Navigate calendar to target date ──────────────────────────────────
    private void navigateCalendarToDate(WebDriver driver,
            String targetDate)
            throws InterruptedException {

// ✅ Parse target month and year from date string
String[] parts    = targetDate.split("-");
int targetYear    = Integer.parseInt(parts[0]);
int targetMonth   = Integer.parseInt(parts[1]);

String[] monthNames = {
"", "January", "February", "March", "April",
"May", "June", "July", "August", "September",
"October", "November", "December"
};

System.out.println("Navigating to: "
+ monthNames[targetMonth] + " " + targetYear);

for (int attempt = 0; attempt < 12; attempt++) {

// ✅ Check h3 month header text
try {
List<WebElement> headers = driver.findElements(
By.cssSelector("h3"));
for (WebElement h : headers) {
String text = h.getText().trim();
if (text.contains(monthNames[targetMonth]) &&
text.contains(String.valueOf(targetYear))) {
System.out.println("Month found: "
+ text + " ✅");
return;
}
}
} catch (Exception ignored) {}

// ✅ Also check date span visible
List<WebElement> found = driver.findElements(
By.cssSelector(
"span[data-date='" + targetDate + "']"));
if (!found.isEmpty() && found.get(0).isDisplayed()) {
System.out.println("Date visible ✅");
return;
}

// ✅ Click Next month
try {
WebElement nextBtn = driver.findElement(
By.cssSelector("button[aria-label='Next month']"));
nextBtn.click();
System.out.println("Next month — attempt "
+ (attempt + 1));
Thread.sleep(600);
} catch (Exception e) {
System.out.println("Next month not found");
return;
}
}
System.out.println("⚠️ Could not navigate to: " + targetDate);
}

    // ── Filter steps ──────────────────────────────────────────────────────
    @When("User applies stays {string} filter")
    public void userAppliesStaysFilter(String filterType) {
        switch (filterType) {
            case "none":
                System.out.println("No filter — verifying raw results");
                break;
            case "Free WiFi":
                filtersPage.staysApplyFreeWifiFilter();
                break;
            case "Very good 8+":
                filtersPage.staysApplyVeryGoodFilter();
                break;
            case "extreme":
                filtersPage.staysApplyExtremeFilter();
                break;
            default:
                System.out.println("Unknown filter: " + filterType);
        }
    }

    @Then("{string} should be verified after stays filter")
    public void verifyAfterStaysFilter(String expectedResult) {
        if (expectedResult.contains("List of hotels")) {
            Assert.assertTrue(
                filtersPage.staysIsHotelDisplayed(),
                "Hotels not displayed");
            System.out.println("✅ Hotel list verified");
        } else if (expectedResult.contains("Filtered results")) {
            Assert.assertTrue(
                filtersPage.staysIsFilteredResultDisplayed(),
                "Filtered results not displayed");
            System.out.println("✅ Filtered results verified");
        } else if (expectedResult.contains("No properties")) {
            Assert.assertTrue(
                filtersPage.staysIsNoResultsDisplayed(),
                "Extreme filter did not reduce results");
            System.out.println("✅ Extreme filter verified");
        }
    }
}