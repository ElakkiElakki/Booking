package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import base.baseclass;

public class CR_ProtectionPage extends baseclass {

    WebDriverWait wait;

    public CR_ProtectionPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // 🔹 LOCATORS

    By withProtectionBtn = By.xpath("//span[text()='With Full Protection']");
    By withoutProtectionBtn = By.xpath("//span[text()='Without Full Protection']");

    // 🔹 CLICK WITH PROTECTION
    public void clickWithProtection() {

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(withProtectionBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", btn);

        System.out.println("✅ Clicked WITH protection");

        wait.until(ExpectedConditions.urlContains("book"));
    }

    // 🔹 CLICK WITHOUT PROTECTION
    public void clickWithoutProtection() {

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(withoutProtectionBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", btn);

        System.out.println("✅ Clicked WITHOUT protection");

        wait.until(ExpectedConditions.urlContains("book"));
    }

    // 🔹 GET URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}