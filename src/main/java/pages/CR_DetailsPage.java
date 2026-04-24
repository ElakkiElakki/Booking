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
    WebElement carName; // car name

    @FindBy(xpath = "//button[@data-testid='go-to-extras-button']")
    WebElement continueBtn; // continue button
    
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
        return carName.getText();
    }

    // click continue
    public void clickContinue() {

        wait.until(ExpectedConditions.elementToBeClickable(continueBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", continueBtn);

        continueBtn.click();

        // 🔥 WAIT FOR NEXT PAGE
        wait.until(d -> d.getCurrentUrl().contains("extras"));
    }
}