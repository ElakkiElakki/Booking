package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import base.baseclass;

public class CR_ExtrasPage extends baseclass {

    WebDriverWait wait;

    public CR_ExtrasPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this); // initialize elements
    }

    // 🔹 HEADER
    @FindBy(xpath = "//*[contains(text(),'Add extras')]")
    WebElement extrasHeader; // extras page header

    // 🔹 GPS BUTTON (same locator kept)
    @FindBy(xpath = "(//button[@type='button'])[15]")
    WebElement gpsPlus; // gps plus button

    // 🔹 CONTINUE BUTTON
    @FindBy(xpath = "//span[text()='Continue to book']")
    WebElement continueBtn; // continue button

    // 🔹 FINAL PRICE (used for wait)
    @FindBy(xpath = "//*[contains(text(),'Price for 2 days')]")
    WebElement finalPrice; // final price label
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // 🔹 VERIFY PAGE
    public boolean isExtrasPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(extrasHeader)).isDisplayed(); // check header
    }

    // 🔹 GET BASE PRICE
    public double getBasePrice() {

        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Price for')]/following::*[contains(text(),'₹')][1]")
        )); // locate base price

        String text = priceElement.getText(); // get text

        System.out.println("🔎 Raw Base Price Text: " + text);

        text = text.replaceAll("[^0-9.]", ""); // remove symbols

        double price = Double.parseDouble(text); // convert to double

        System.out.println("💰 Base Price: " + price);

        return price;
    }

    // 🔹 SELECT GPS
    public void selectGPS() {

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(gpsPlus)); // wait for gps

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", btn); // scroll

        btn.click(); // click gps

        System.out.println("✅ GPS selected");

        waitForPriceUpdate(); // wait for update
    }

    // 🔹 GET EXTRA PRICE (GENERIC)
    public double getExtraPrice(String extraName) {

        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + extraName + "')]/ancestor::div[contains(@class,'package')]")
        )); // find extra card

        String text = card.findElement(
                By.xpath(".//*[contains(text(),'₹')]")
        ).getText(); // get price

        System.out.println("🔎 Raw " + extraName + " Price Text: " + text);

        text = text.replaceAll("[^0-9.]", ""); // clean text

        double price = Double.parseDouble(text); // convert

        System.out.println("➕ " + extraName + " Price: " + price);

        return price;
    }

    // 🔹 GET FINAL PRICE
    public double getFinalPrice() {

        java.util.List<WebElement> prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//*[contains(text(),'To pay at pick-up')]/following::*[contains(text(),'₹')]")
        )); // get all prices

        WebElement totalElement = prices.get(prices.size() - 1); // last = total

        String text = totalElement.getText(); // get text

        System.out.println("🔎 Raw Final Price Text: " + text);

        text = text.replaceAll("[^0-9.]", ""); // clean

        double price = Double.parseDouble(text); // convert

        System.out.println("💰 Final Price: " + price);

        return price;
    }

    // 🔹 CLICK CONTINUE
    public void clickContinue() {

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueBtn)); // wait

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", btn); // scroll

        btn.click(); // click

        System.out.println("✅ Continue clicked from Extras");

        // wait for next page
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> !d.getCurrentUrl().contains("extras"));

        System.out.println("📍 After Extras → " + driver.getCurrentUrl());
    }

    // 🔹 WAIT FOR PRICE UPDATE (KEEP SAME LOGIC)
    private void waitForPriceUpdate() {
        try {
            Thread.sleep(2000); // small UI refresh wait
        } catch (Exception e) {
        }
    }
}
