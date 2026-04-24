package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ST_ResultsPage extends baseclass {

    WebDriverWait wait;

    @FindBy(css = "h1[aria-live='assertive']")
    WebElement resultsHeading;

    public ST_ResultsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ── Click first hotel from results ────────────────────────────────────
    public void staysClickFirstHotel() throws InterruptedException {
        Thread.sleep(2000);

        // Close any overlay
        try {
            driver.findElement(By.tagName("body"))
                  .sendKeys(Keys.ESCAPE);
        } catch (Exception ignored) {}

        // Click hotel title link
        try {
            WebElement hotelLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("[data-testid='title-link']")));
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", hotelLink);
            System.out.println("Hotel clicked ✅");
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("Hotel click error: " + e.getMessage());
        }
    }

    // ── Check results page ────────────────────────────────────────────────
    public boolean staysIsResultsPageDisplayed() {
        try {
            return af.isDisplayed(resultsHeading);
        } catch (Exception e) {
            return false;
        }
    }
}