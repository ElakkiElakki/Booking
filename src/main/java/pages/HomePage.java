package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.AllFunctionalities;

import java.time.Duration;

public class HomePage extends baseclass {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openApplication() {
        driver.get(AllFunctionalities.getConfig("baseUrl"));
        System.out.println("Booking website opened");
    }

    public boolean isHomePageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.name("ss")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Where')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//form")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//header"))
            ));

            return driver.findElements(By.name("ss")).size() > 0
                    || driver.findElements(By.xpath("//input[contains(@placeholder,'Where')]")).size() > 0
                    || driver.findElements(By.xpath("//form")).size() > 0
                    || driver.findElements(By.xpath("//header")).size() > 0;

        } catch (Exception e) {
            return false;
        }
    }
}