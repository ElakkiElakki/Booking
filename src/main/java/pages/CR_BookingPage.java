package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import base.baseclass;


public class CR_BookingPage extends baseclass {

    WebDriverWait wait;

    public CR_BookingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "firstName")
    WebElement firstName; // first name

    @FindBy(name = "lastName")
    WebElement lastName; // last name

    @FindBy(id = ":Rakudagp:")
    WebElement phone; // phone

    @FindBy(xpath = "//input[@placeholder='House/apartment number and street name']")
    WebElement address; // address

    @FindBy(xpath="//input[@data-testid='billingAddressCity-field']")
    WebElement city; // city

    @FindBy(xpath = "//input[@data-testid='billingAddressPostcode-field']")
    WebElement postcode; // postcode

    
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // fill form
    public void fillForm(String fn, String ln, String ph, String addr, String cty, String pc) {
        wait.until(ExpectedConditions.visibilityOf(firstName)).sendKeys(fn);
        lastName.sendKeys(ln);
        phone.sendKeys(ph);
        address.sendKeys(addr);
        city.sendKeys(cty);
        postcode.sendKeys(pc);
    }

    
}