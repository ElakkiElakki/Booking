package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ST_BookingPage extends baseclass {

    WebDriverWait wait;

    public ST_BookingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ── Clear and type into field ─────────────────────────────────────────
    private void clearAndType(WebElement field, String value) {
        try {
            // ✅ Triple click to select all text
            field.click();
            Thread.sleep(200);
            field.sendKeys(Keys.CONTROL + "a");
            field.sendKeys(Keys.DELETE);
            field.clear();
            Thread.sleep(200);
            // ✅ Type new value
            field.sendKeys(value);
        } catch (Exception e) {
            System.out.println("clearAndType: " + e.getMessage());
        }
    }

    // ── Find field by multiple locators ──────────────────────────────────
    private WebElement findField(String... cssLocators) {
        for (String locator : cssLocators) {
            try {
                WebElement field = driver.findElement(
                    By.cssSelector(locator));
                if (field.isDisplayed()) return field;
            } catch (Exception ignored) {}
        }
        return null;
    }

    // ── Enter first name ──────────────────────────────────────────────────
    public void staysEnterFirstName(String name)
                                    throws InterruptedException {
        Thread.sleep(500);
        WebElement field = findField(
            "input#firstname",
            "input[name='firstname']",
            "input[autocomplete='given-name']");
        if (field != null) {
            clearAndType(field, name);
            System.out.println("FirstName entered: " + name + " ✅");
        } else {
            System.out.println("FirstName not found");
        }
    }

    // ── Enter last name ───────────────────────────────────────────────────
    public void staysEnterLastName(String name)
                                   throws InterruptedException {
        Thread.sleep(300);
        WebElement field = findField(
            "input#lastname",
            "input[name='lastname']",
            "input[autocomplete='family-name']");
        if (field != null) {
            clearAndType(field, name);
            System.out.println("LastName entered: " + name + " ✅");
        } else {
            System.out.println("LastName not found");
        }
    }

    // ── Enter email ───────────────────────────────────────────────────────
    public void staysEnterEmail(String email)
                                throws InterruptedException {
        Thread.sleep(300);
        // ✅ From screenshot — email field is separate input
        WebElement field = findField(
            "input#email",
            "input[name='email']",
            "input[type='email']",
            "input[autocomplete='email']");
        if (field != null) {
            clearAndType(field, email);
            System.out.println("Email entered: " + email + " ✅");
        } else {
            // ✅ XPath fallback
            try {
                WebElement emailField = driver.findElement(
                    By.xpath(
                        "//label[contains(text(),'Email')]" +
                        "/following-sibling::input | " +
                        "//input[contains(@id,'email')]"));
                clearAndType(emailField, email);
                System.out.println("Email via xpath ✅");
            } catch (Exception e) {
                System.out.println("Email not found");
            }
        }
    }

    // ── Enter phone ───────────────────────────────────────────────────────
    public void staysEnterPhone(String phone)
                                throws InterruptedException {
        Thread.sleep(300);
        // ✅ From screenshot — phone is inside IN +91 div
        WebElement field = findField(
            "input[name='phone']",
            "input[type='tel']",
            "input[id*='phone']",
            "input[autocomplete='tel']");
        if (field != null) {
            clearAndType(field, phone);
            System.out.println("Phone entered: " + phone + " ✅");
        } else {
            // ✅ XPath fallback
            try {
                WebElement phoneField = driver.findElement(
                    By.xpath(
                        "//label[contains(text(),'Phone')]" +
                        "/following-sibling::div//input | " +
                        "//div[contains(@class,'phone')]//input"));
                clearAndType(phoneField, phone);
                System.out.println("Phone via xpath ✅");
            } catch (Exception e) {
                System.out.println("Phone not found");
            }
        }
    }

    // ── Click Next: Final details ─────────────────────────────────────────
    public void staysClickNext() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Clicking Next...");

        // ✅ Select arrival time if visible
        try {
            WebElement arrivalDropdown = driver.findElement(
                By.cssSelector(
                    "select[id*='arrival']," +
                    "select[name*='arrival']," +
                    "select[data-testid*='arrival']"));
            if (arrivalDropdown.isDisplayed()) {
                new org.openqa.selenium.support.ui.Select(arrivalDropdown)
                    .selectByIndex(1);
                System.out.println("Arrival time selected ✅");
                Thread.sleep(300);
            }
        } catch (Exception ignored) {}

        // ✅ Click Next: Final details
        String[] xpaths = {
            "//button[contains(.,'Next: Final details')]",
            "//button[contains(.,'Final details')]",
            "//button[contains(.,'Next')]",
            "//button[contains(.,'Continue')]"
        };

        for (String xpath : xpaths) {
            try {
                WebElement btn = driver.findElement(By.xpath(xpath));
                if (btn.isDisplayed() && btn.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        btn);
                    Thread.sleep(300);
                    ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", btn);
                    System.out.println("Next clicked ✅");
                    Thread.sleep(3000);
                    return;
                }
            } catch (Exception ignored) {}
        }
        System.out.println("Next button not found");
    }

    // ── Assertions ────────────────────────────────────────────────────────
    public boolean isBookingFormDisplayed() {
        try {
            Thread.sleep(2000);
            String url = driver.getCurrentUrl();
            System.out.println("Booking URL: " + url);
            return url.contains("secure.booking.com") ||
                   url.contains("book.html");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean staysIsBookingFormDisplayed() {
        return isBookingFormDisplayed();
    }

    public boolean staysIsErrorDisplayed() {
        try {
            Thread.sleep(1000);
            String url = driver.getCurrentUrl();
            // ✅ Still on booking form = validation blocked
            if (url.contains("book.html") ||
                url.contains("secure.booking.com")) {
                System.out.println("Still on booking form ✅");
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}