package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import base.baseclass;


public class CR_ResultsPage extends baseclass {

    WebDriverWait wait;

    public CR_ResultsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[contains(.,'cars') or contains(.,'Cars')]")
    WebElement resultCount; // result count

    @FindBy(xpath = "//div[@role='dialog']//button")
    WebElement popupClose; // popup close

    @FindBy(xpath = "//input[@data-testid='depotLocationType-filter-option-IN_TERMINAL']")
    WebElement airport; // airport filter

    @FindBy(xpath = "//input[@data-testid='carCategory-filter-option-large']")
    WebElement largeCar; // large car filter

    @FindBy(xpath = "//input[@data-testid='pricePerDayBuckets-filter-option-BUCKET_2']")
    WebElement price; // price filter

    @FindBy(xpath = "//button[contains(.,'Clear')]")
    WebElement clearBtn; // clear filters

    // close popup
    public void handlePopupIfPresent() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(popupClose)).click();
        } catch (Exception e) {
            System.out.println("No popup");
        }
    }

    // get count
    public int getResultCount() {
        String text = wait.until(ExpectedConditions.visibilityOf(resultCount)).getText();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    // apply filters
    public void applyAllFilters() {
        selectCheckbox(airport);
        selectCheckbox(largeCar);
        selectCheckbox(price);
    }

    // select checkbox
    public void selectCheckbox(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        if (!element.isSelected()) {
            element.click();
        }
    }

    // clear filters
    public void clearAllFilters() {
        try {
            clearBtn.click();
        } catch (Exception e) {
            System.out.println("Clear not visible");
        }
    }
}