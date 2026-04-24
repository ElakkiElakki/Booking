package pages;

import base.baseclass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ST_EndToEndPage extends baseclass {

    public ST_EndToEndPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ✅ Check if booking page is displayed
    public boolean staysIsFinishPageDisplayed() {
        try {
            Thread.sleep(2000);
            String url = driver.getCurrentUrl();
            System.out.println("E2E URL: " + url);

            // ✅ Payment page = stage=2 or finaldetails
            boolean isFinished =
                url.contains("stage=2") ||
                url.contains("finaldetails") ||
                url.contains("payment") ||
                url.contains("confirmation") ||
                // ✅ Still on book.html but moved forward
                (url.contains("book.html") &&
                 !url.contains("stage=1"));

            // ✅ Also check for payment elements on page
            if (!isFinished) {
                try {
                    WebElement payBtn = driver.findElement(
                        By.xpath(
                            "//button[contains(.,'Complete booking')]" +
                            "| //button[contains(.,'Complete')]"));
                    if (payBtn.isDisplayed()) {
                        System.out.println(
                            "Payment page — Complete booking button ✅");
                        return true;
                    }
                } catch (Exception ignored) {}

                // ✅ Check for payment form
                try {
                    WebElement cardField = driver.findElement(
                        By.cssSelector(
                            "input[name*='card']," +
                            "input[id*='card']," +
                            "[data-testid*='card']"));
                    if (cardField.isDisplayed()) {
                        System.out.println("Payment form visible ✅");
                        return true;
                    }
                } catch (Exception ignored) {}
            }

            System.out.println("isFinished: " + isFinished);
            return isFinished;

        } catch (Exception e) {
            System.out.println("isFinishPageDisplayed: "
                + e.getMessage());
            return false;
        }
    }
}