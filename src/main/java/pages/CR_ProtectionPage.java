package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import base.baseclass;


public class CR_ProtectionPage extends baseclass {

    WebDriverWait wait;

    public CR_ProtectionPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(.,'With Full Protection')]")
    WebElement withProtection; // with protection

    @FindBy(xpath = "//button[contains(.,'Without Full Protection')]")
    WebElement withoutProtection; // without protection

    // click with protection
    public void clickWithProtection() {
        wait.until(ExpectedConditions.elementToBeClickable(withProtection)).click();
    }

    // click without protection
    public void clickWithoutProtection() {
        wait.until(ExpectedConditions.elementToBeClickable(withoutProtection)).click();
    }
}