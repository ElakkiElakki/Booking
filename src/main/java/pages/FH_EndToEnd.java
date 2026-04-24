package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class FH_EndToEnd {

    WebDriver driver;
    WebDriverWait wait;

    public FH_EndToEnd(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    JavascriptExecutor js() {
        return (JavascriptExecutor) driver;
    }

    // ================= COMMON =================
    public void handleAutoFillPopup() {
        try {
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
            js().executeScript("document.body.click();");
        } catch (Exception ignored) {}
    }

    public boolean isPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    // ================= INPUT =================
    public void enterReactText(By locator, String value) {

        if (value == null || value.trim().isEmpty()) return;

        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        js().executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        js().executeScript("arguments[0].click();", el);

        js().executeScript("arguments[0].value='';", el);
        el.sendKeys(value);

        js().executeScript("arguments[0].dispatchEvent(new Event('input',{bubbles:true}));", el);
        js().executeScript("arguments[0].dispatchEvent(new Event('change',{bubbles:true}));", el);
        js().executeScript("arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));", el);
    }

    // ================= DROPDOWN =================
    public void selectDropdown(By buttonLocator, String value) {

        if (value == null || value.trim().isEmpty()) return;

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

        js().executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        js().executeScript("arguments[0].click();", btn);

        By option = By.xpath("(//span[normalize-space()='" + value + "'])[last()]");

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(option));

        js().executeScript("arguments[0].click();", el);

        System.out.println("✅ Selected: " + value);
    }

    // ================= CONTACT =================
    public void fillContactDetails(String fn, String ln, String mail,
                                   String ph, String addr, String house,
                                   String pin, String cityName) {

        enterReactText(By.name("name"), fn);
        enterReactText(By.name("surname"), ln);
        enterReactText(By.name("email"), mail);
        enterReactText(By.name("phone"), ph);
        enterReactText(By.name("address"), addr);
        enterReactText(By.name("houseNumber"), house);
        enterReactText(By.name("postCode"), pin);
        enterReactText(By.name("city"), cityName);

        System.out.println("✅ Contact details filled");
    }

    // ================= TRAVELLER =================
    public void fillTraveller(int i, String gender, String first,
                              String last, String day,
                              String month, String year,
                              String nationality,
                              String docNumber,
                              String issueCountry,
                              String issueDay,
                              String issueMonth,
                              String issueYear,
                              String expDay,
                              String expMonth,
                              String expYear) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(),'Traveller " + i + "')]")
        ));

        handleAutoFillPopup();

        // ✅ Gender
        String g = gender.equalsIgnoreCase("Mr") ? "MALE" : "FEMALE";

        By genderBtn = By.xpath("//input[@name='groups.1.travellers." + i + ".title' and @value='" + g + "']");
        js().executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.presenceOfElementLocated(genderBtn)));

        // ✅ Name
     // ✅ Name (FINAL FIX - precise + visible only)
        By firstNameLocator = By.xpath(
            "//input[@name='groups.1.travellers." + i + ".name' and not(@type='hidden')]"
        );

        By lastNameLocator = By.xpath(
            "//input[@name='groups.1.travellers." + i + ".surname' and not(@type='hidden')]"
        );

        enterReactText(firstNameLocator, first);
        enterReactText(lastNameLocator, last);

        // ✅ DOB
        enterReactText(By.xpath("//div[@data-testid='groups.1.travellers." + i + ".dateOfBirth_day']//input"), day);

        selectDropdown(
                By.xpath("//button[@data-testid='groups.1.travellers." + i + ".dateOfBirth_month']"),
                month
        );

        enterReactText(By.xpath("//div[@data-testid='groups.1.travellers." + i + ".dateOfBirth_year']//input"), year);

        // ================= OPTIONAL =================

        if (isPresent(By.xpath("//button[@data-testid='groups.1.travellers." + i + ".nationality']"))) {
            selectDropdown(
                    By.xpath("//button[@data-testid='groups.1.travellers." + i + ".nationality']"),
                    nationality
            );
        }

        // 🔥 Passport ONLY if present + data available
        if (docNumber != null && !docNumber.isEmpty()
                && isPresent(By.name("groups.1.travellers." + i + ".documentNumber"))) {

            enterReactText(
                    By.name("groups.1.travellers." + i + ".documentNumber"),
                    docNumber
            );

            selectDropdown(
                    By.xpath("//button[@data-testid='groups.1.travellers." + i + ".documentIssueCountry']"),
                    issueCountry
            );

            enterReactText(
                    By.xpath("//div[@data-testid='groups.1.travellers." + i + ".documentIssueDate_day']//input"),
                    issueDay
            );

            selectDropdown(
                    By.xpath("//button[@data-testid='groups.1.travellers." + i + ".documentIssueDate_month']"),
                    issueMonth
            );

            enterReactText(
                    By.xpath("//div[@data-testid='groups.1.travellers." + i + ".documentIssueDate_year']//input"),
                    issueYear
            );

            enterReactText(
                    By.xpath("//div[@data-testid='groups.1.travellers." + i + ".documentExpiryDate_day']//input"),
                    expDay
            );

            selectDropdown(
                    By.xpath("//button[@data-testid='groups.1.travellers." + i + ".documentExpiryDate_month']"),
                    expMonth
            );

            enterReactText(
                    By.xpath("//div[@data-testid='groups.1.travellers." + i + ".documentExpiryDate_year']//input"),
                    expYear
            );
        }

        System.out.println("✅ Traveller " + i + " filled");
    }

    // ================= FINAL =================
    public void acceptPolicyAndProceed() throws InterruptedException {

        // ✅ Click checkbox
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@data-test='privacyPolicy-check']")
        ));

        js().executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
        Thread.sleep(500);
        js().executeScript("arguments[0].click();", checkbox);

        System.out.println("✅ Checkbox clicked");

        // ✅ Wait small time for React validation
        Thread.sleep(1500);

        // ✅ Locate Next button
        WebElement nextBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[@data-test='lead-generation-submit-btn']")
        ));

        // 🔥 STEP 1: Scroll
        js().executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);

        // 🔥 STEP 2: Wait until enabled
        wait.until(driver -> nextBtn.isEnabled());

        // 🔥 STEP 3: Try NORMAL click first
        try {
            nextBtn.click();
            System.out.println("✅ Normal click worked");
        } catch (Exception e) {

            // 🔥 STEP 4: Actions click
            try {
                new Actions(driver).moveToElement(nextBtn).click().perform();
                System.out.println("✅ Actions click worked");
            } catch (Exception ex) {

                // 🔥 STEP 5: JS click fallback
                js().executeScript("arguments[0].click();", nextBtn);
                System.out.println("✅ JS click fallback used");
            }
        }

        // 🔥 STEP 6: FORCE React event (VERY IMPORTANT)
        js().executeScript(
                "arguments[0].dispatchEvent(new Event('click', {bubbles:true}));",
                nextBtn
        );

        // 🔥 STEP 7: Wait for navigation
        Thread.sleep(4000);
    }

    public void verifyPaymentSection() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'How would you like to pay')]")
        ));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("creditCard.cardNumber")
        ));

        System.out.println("✅ Payment section loaded");
    }
}