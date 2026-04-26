package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;


import base.baseclass;

/**
 * Page class for Car Search functionality
 */
public class CR_SearchPage extends baseclass {

    WebDriverWait wait;

    public CR_SearchPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this); // initialize elements
    }

    @FindBy(name = "pickup-location")
    WebElement pickup; // pickup field

    @FindBy(name = "dropoff-location")
	public
    WebElement drop; // drop field

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchBtn; // search button

    @FindBy(name = "drop-off-at-different-loc")
    WebElement checkbox; // check-box
    
    @FindBy(xpath = "//button[contains(@aria-label,'Pick-up Date')]")
    WebElement datePicker;

    @FindBy(xpath = "//span[@data-date='2026-04-28']")
    WebElement startDate;

    @FindBy(xpath = "//span[@data-date='2026-04-30']")
    WebElement endDate;
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // open site
    public void openCarRentalPage() {
        driver.navigate().to("https://www.booking.com/cars/index.html");
    }

    // enter pickup
    public void enterPickup(String value) {

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(pickup));

        input.click();
        input.clear();
        input.sendKeys(value);

        // 🔥 Wait for suggestion list
        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//div[contains(@role,'listbox')]//div)[3]")
        ));

        // 🔥 Click first suggestion instead of keyboard
        firstOption.click();

        System.out.println("✅ Pickup selected");
    }

    // click check-box
    public void clickCheckbox() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        wait.until(ExpectedConditions.visibilityOf(drop));
    }

//     enter drop
    public void enterDrop(String value) {

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(drop));

        input.click();
        input.clear();
        input.sendKeys(value);

        // 🔥 Wait for suggestion list
        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//div[contains(@role,'listbox')]//div)[3]")
        ));

        firstOption.click();

        System.out.println("✅ Drop selected");
    }
    
    //enter invalid drop
    public void enterInvalidDrop(String value) {

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(drop));

        input.click();
        input.clear();
        input.sendKeys(value);

        System.out.println("⚠️ Invalid drop entered");
    }
    
    //select date
    public void selectDate() {
    	datePicker.click();

    	wait.until(ExpectedConditions.elementToBeClickable(startDate));
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", startDate);

    	wait.until(ExpectedConditions.elementToBeClickable(endDate));
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", endDate);
    }

    // click search
    public void clickSearch() {
        searchBtn.click();
       
    }
}