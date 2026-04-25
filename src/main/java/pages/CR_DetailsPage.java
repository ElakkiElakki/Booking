package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import base.baseclass;


public class CR_DetailsPage extends baseclass {

    WebDriverWait wait;

    public CR_DetailsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//h2//div)[1]")
    WebElement carName;

    @FindBy(xpath = "//*[contains(text(),'Airport')]")
    WebElement pickup;

    @FindBy(xpath = "//*[contains(text(),'Dubai')]")
    WebElement drop;

    @FindBy(xpath = "//*[contains(text(),'costing you just')]")
    WebElement price;

    @FindBy(xpath = "//button[@data-testid='go-to-extras-button']")
    WebElement continueBtn;
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // switch tab
    public void switchToNewTab() {
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
        }
        wait.until(ExpectedConditions.visibilityOf(carName));
    }
        

    // get name
    public String getCarName() {
        return wait.until(ExpectedConditions.visibilityOf(carName)).getText();
    }
    public String getPickup() {

        wait.until(ExpectedConditions.visibilityOf(pickup));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", pickup);

        return pickup.getText();
    }
    public String getDrop() {

        wait.until(ExpectedConditions.visibilityOf(drop));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", drop);

        return drop.getText();
    }
    public String getPrice() {

        wait.until(ExpectedConditions.visibilityOf(price));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", price);

        String text = price.getText();

        System.out.println("Full Price Text: " + text);

        return text;
    }
    // click continue
    public void clickContinue() {

        wait.until(ExpectedConditions.elementToBeClickable(continueBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", continueBtn);

        continueBtn.click();

        wait.until(ExpectedConditions.urlContains("extras"));

        System.out.println("✅ Clicked Continue");
    }
}