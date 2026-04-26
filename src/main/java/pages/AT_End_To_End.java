package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class AT_End_To_End extends baseclass {

    private final By destinationField = By.xpath(
            "//input[contains(@placeholder,'Destination') or contains(@placeholder,'Where') or @name='query']" +
            " | //label[contains(.,'Destination')]/following::input[1]"
    );

    private final By galleryImage = By.xpath(
            "(//div[@data-testid='gallery-slider-slide']//img)[1]" +
            " | (//div[@data-testid='gallery-slider']//img)[1]" +
            " | (//img[contains(@src,'xphoto')])[1]"
    );

    private final By galleryPopup = By.xpath(
            "//div[@role='dialog'][.//img]" +
            " | //div[contains(@data-testid,'gallery') and .//img]"
    );

    private final By popupClose = By.xpath(
            "//div[@role='dialog']//button[contains(@aria-label,'Close')]" +
            " | //button[contains(@aria-label,'Dismiss')]"
    );

    private final By selectTicketsButton = By.xpath(
            "//button[.//span[contains(text(),'Select tickets')]]" +
            " | //button[contains(.,'Select tickets')]"
    );
    
    private final By finalSelectButton = By.xpath(
            "(//button[@data-testid='select-ticket'])[1]" +
            " | (//span[normalize-space()='Select']/ancestor::button[1])[1]"
    );
    
    private final By plusSvgButton = By.xpath(
    		"//span[contains(normalize-space(),'2 adults, 1 child')]"
    	);

    private final By nextButton = By.xpath(
            "//button[normalize-space()='Next']" +
            " | //button[contains(.,'Next')]"
    );
    
    private final By firstNameField = By.name("firstName");
    private final By lastNameField  = By.name("lastName");
    private final By emailField     = By.name("email");
    private final By phoneField     = By.name("phone__number");

    private final By paymentDetailsButton = By.xpath(
            "//div[@data-testid='sticky-cta-container']//button[.//span[normalize-space()='Payment details']]" +
            " | //button[.//span[normalize-space()='Payment details']]" +
            " | //span[normalize-space()='Payment details']/ancestor::button[1]"
    );

   
    
    public AT_End_To_End(WebDriver driver) {
        super(driver);
    }

    public void launchAttractionsForGalleryImage() {
        driver.get("https://www.booking.com/attractions/");
        af.hardWait(3);
    }

    public void searchDestinationForGalleryImage(String destination) {
        WebElement field = af.waitForVisible(destinationField);

        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.DELETE);
        field.sendKeys(destination);

        af.hardWait(3);

        List<WebElement> suggestions = driver.findElements(By.xpath(
                "//li[@role='option']" +
                " | //div[@role='option']" +
                " | //ul/li" +
                " | //div[contains(@data-testid,'autocomplete')]//*[self::li or self::div]"
        ));

        for (WebElement suggestion : suggestions) {
            try {
                if (suggestion.isDisplayed()
                        && suggestion.getText() != null
                        && suggestion.getText().toLowerCase().contains(destination.toLowerCase())) {
                    suggestion.click();
                    af.hardWait(2);
                    break;
                }
            } catch (Exception ignored) {
            }
        }

        clickSearchButton();
    }

    private void clickSearchButton() {
        List<By> searchButtons = List.of(
                By.xpath("//button[@data-testid='search-button']"),
                By.xpath("//button[@type='submit']"),
                By.xpath("//button[contains(normalize-space(),'Search')]"),
                By.xpath("//span[contains(normalize-space(),'Search')]/ancestor::button[1]")
        );

        for (By locator : searchButtons) {
            try {
                List<WebElement> buttons = driver.findElements(locator);

                for (WebElement button : buttons) {
                    if (!button.isDisplayed() || !button.isEnabled()) {
                        continue;
                    }

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center'});", button);

                    af.hardWait(1);

                    try {
                        button.click();
                    } catch (Exception e) {
                        af.jsClick(button);
                    }

                    System.out.println("Search button clicked using: " + locator);
                    af.hardWait(5);
                    return;
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Search button not clicked.");
    }

    public void openFirstDetailsPageForGalleryImage() {
        af.hardWait(5);

        List<By> detailLocators = List.of(
                By.xpath("(//a[contains(@href,'/attractions/') and not(contains(@href,'searchresults')) and not(contains(@href,'index'))])[1]"),
                By.xpath("(//a[contains(.,'See availability')])[1]"),
                By.xpath("(//button[contains(.,'See availability')])[1]"),
                By.xpath("(//a[contains(.,'Select date')])[1]"),
                By.xpath("(//button[contains(.,'Select date')])[1]")
        );

        for (By locator : detailLocators) {
            try {
                List<WebElement> elements = driver.findElements(locator);

                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                        continue;
                    }

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center'});", element);

                    af.hardWait(1);

                    String beforeUrl = driver.getCurrentUrl();
                    String oldWindow = driver.getWindowHandle();
                    Set<String> oldWindows = driver.getWindowHandles();

                    try {
                        element.sendKeys(Keys.ENTER);
                    } catch (Exception e1) {
                        try {
                            element.click();
                        } catch (Exception e2) {
                            af.jsClick(element);
                        }
                    }

                    af.hardWait(5);

                    Set<String> newWindows = driver.getWindowHandles();

                    if (newWindows.size() > oldWindows.size()) {
                        for (String win : newWindows) {
                            if (!oldWindows.contains(win)) {
                                driver.switchTo().window(win);
                                break;
                            }
                        }
                    } else {
                        driver.switchTo().window(oldWindow);
                    }

                    String afterUrl = driver.getCurrentUrl();

                    if (!afterUrl.equals(beforeUrl) && !afterUrl.contains("searchresults")) {
                        System.out.println("Opened details page URL: " + afterUrl);
                        return;
                    }
                }

            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Could not open attraction details page. Current URL: " + driver.getCurrentUrl());
    }

    public void closeAnyPopupIfPresent() {
        try {
            List<WebElement> closeButtons = driver.findElements(popupClose);

            for (WebElement close : closeButtons) {
                if (close.isDisplayed()) {
                    af.jsClick(close);
                    af.hardWait(2);
                    System.out.println("Popup closed.");
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void clickGalleryImageInDetailsPage() {
        af.hardWait(5);

        closeAnyPopupIfPresent();

        WebElement image = af.waitForVisible(galleryImage);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", image);

        af.hardWait(1);

        try {
            image.click();
        } catch (Exception e) {
            af.jsClick(image);
        }

        af.hardWait(4);

        System.out.println("Clicked actual gallery image.");
    }

    public boolean isGalleryPopupDisplayed() {
        try {
            List<WebElement> popups = driver.findElements(galleryPopup);

            for (WebElement popup : popups) {
                if (popup.isDisplayed()) {
                    System.out.println("Gallery popup displayed.");
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public void clickSelectTicketsButton() {

        WebElement button = af.waitForVisible(By.xpath(
                "//button[.//span[contains(text(),'Select tickets')]]" +
                " | //button[contains(.,'Select tickets')]"
        ));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", button);

        af.hardWait(1);

        try {
            button.click();
        } catch (Exception e) {
            af.jsClick(button);
        }

        af.hardWait(3);

        System.out.println("Clicked Select Tickets button.");
    }
    
    public void clickFinalSelectButton() {

        WebElement button = af.waitForVisible(finalSelectButton);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", button);

        af.hardWait(1);

        try {
            button.click();
        } catch (Exception e) {
            af.jsClick(button);
        }

        af.hardWait(3);

        System.out.println("Clicked final Select button.");
    }
    
    public void clickPlusSvgButton() {

        WebElement button = af.waitForVisible(plusSvgButton);

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", button);

        af.hardWait(1);

        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", button);
        }

        af.hardWait(2);
        System.out.println("Clicked SVG plus button.");
    }
    public void clickNextButton() {

        WebElement next = af.waitForVisible(nextButton);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", next);

        af.hardWait(1);

        try {
            next.click();
        } catch (Exception e) {
            af.jsClick(next);
        }

        af.hardWait(3);

        System.out.println("Clicked Next button.");
    }
    public void fillDetailsAndClickPaymentButton() {

        af.waitForVisible(By.name("firstName")).sendKeys("Elakki");
        af.waitForVisible(By.name("lastName")).sendKeys("Kumar");
        af.waitForVisible(By.name("email")).sendKeys("elakki@test.com");
        af.waitForVisible(By.name("phone__number")).sendKeys("9876543210");

        String beforeUrl = driver.getCurrentUrl();

        WebElement button = af.waitForVisible(paymentDetailsButton);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", button);

        af.hardWait(1);

        try {
            button.click();
        } catch (Exception e) {
            af.jsClick(button);
        }

        af.hardWait(5);

        String afterUrl = driver.getCurrentUrl();

        if (afterUrl.equals(beforeUrl)) {
            throw new RuntimeException("Payment details button was not clicked / page did not move.");
        }

        System.out.println("Clicked Payment details button. New URL: " + afterUrl);
    }
}