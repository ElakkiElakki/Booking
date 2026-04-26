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
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(pickup));
        input.click();
        input.sendKeys(value);
        try {
            Thread.sleep(4000);
            input.click();
            input.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(900);
            input.sendKeys(Keys.ENTER);
        } catch (Exception e) {}
    }

    // click check-box
//    public void clickCheckbox() {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
//        wait.until(ExpectedConditions.visibilityOf(drop));
//    }
    public void clickCheckbox() {

        By checkboxLocator = By.xpath(
            "//input[@name='drop-off-at-different-loc']" +
            " | //label[contains(.,'Return car to different location')]" +
            " | //label[contains(.,'different location')]"
        );

        By dropLocator = By.xpath(
            "//input[@name='dropoff-location']" +
            " | //input[contains(@placeholder,'Drop-off')]" +
            " | //input[contains(@aria-label,'Drop-off')]"
        );

        try {
            WebElement check = wait.until(
                ExpectedConditions.presenceOfElementLocated(checkboxLocator)
            );

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", check
            );

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", check);

            System.out.println("Different drop-off checkbox clicked");

        } catch (Exception e) {
            System.out.println("Checkbox not found or already selected");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(dropLocator));
    }
    // enter drop
    public void enterDrop(String value) {
    	WebElement input = new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.elementToBeClickable(drop));
        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.clear();
        input.sendKeys(value);
        try {
            Thread.sleep(4000);
            input.click();
            input.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(900);
            input.sendKeys(Keys.ENTER);
        } catch (Exception e) {}
    }

    // select date
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