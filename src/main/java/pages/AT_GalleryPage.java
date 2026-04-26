package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
 
public class AT_GalleryPage extends baseclass {

    private final By destinationField = By.xpath(
            "//input[contains(@placeholder,'Destination') or contains(@placeholder,'Where') or @name='query']" +
            " | //label[contains(.,'Destination')]/following::input[1]"
    );

    private final By searchButton = By.xpath(
            "//button[@data-testid='search-button']" +
            " | //button[@type='submit']" +
            " | //button[contains(normalize-space(),'Search')]" +
            " | //span[contains(normalize-space(),'Search')]/ancestor::button[1]"
    );

    private final By galleryVisibleMarker = By.xpath(
            "//div[@data-testid='gallery-slider-slide']" +
            " | //img[contains(@src,'/xdata/images/xphoto/')]" +
            " | //div[@id='attr-product-page-main-content']"
    );

    public AT_GalleryPage(WebDriver driver) {
        super(driver);
    }

    public void openAttractionsPage() {
        driver.get("https://www.booking.com/attractions/");
        af.hardWait(1);
    }

    public void searchDestination(String destination) {
        WebElement field = af.waitForVisible(destinationField);
        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.DELETE);
        field.sendKeys(destination);

        af.hardWait(1);

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
                    af.hardWait(1);
                    clickSearchButton();
                    return;
                }
            } catch (Exception ignored) {
            }
        }

        try {
            field.sendKeys(Keys.ARROW_DOWN);
            af.hardWait(1);
            field.sendKeys(Keys.ENTER);
        } catch (Exception ignored) {
        }

        af.hardWait(1);
        clickSearchButton();
    }

    private void clickSearchButton() {
        List<By> searchLocators = List.of(
                By.xpath("//button[@data-testid='search-button']"),
                By.xpath("//button[@type='submit']"),
                By.xpath("//button[contains(normalize-space(),'Search')]"),
                By.xpath("//span[contains(normalize-space(),'Search')]/ancestor::button[1]")
        );

        for (By locator : searchLocators) {
            try {
                List<WebElement> buttons = driver.findElements(locator);
                if (buttons.isEmpty()) {
                    continue;
                }

                for (WebElement button : buttons) {
                    try {
                        if (!button.isDisplayed() || !button.isEnabled()) {
                            continue;
                        }

                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].scrollIntoView({block:'center'});", button);
                        af.hardWait(1);

                        try {
                            button.click();
                        } catch (Exception e) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                        }

                        System.out.println("Search button clicked using locator: " + locator);
                        af.hardWait(1);
                        return;
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Could not click Search button.");
    }

    public void clickFirstSeeAvailabilityFromResults() {
        af.hardWait(1);

        List<By> resultLocators = List.of(
                By.xpath("(//button[contains(.,'See availability')])[1]"),
                By.xpath("(//a[contains(.,'See availability')])[1]"),
                By.xpath("(//button[contains(.,'Select date')])[1]"),
                By.xpath("(//a[contains(.,'Select date')])[1]"),
                By.xpath("(//button[contains(.,'Check availability')])[1]"),
                By.xpath("(//a[contains(.,'Check availability')])[1]"),
                By.xpath("(//a[contains(@href,'/attractions/')])[1]")
        );

        for (By locator : resultLocators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (elements.isEmpty()) {
                    continue;
                }

                WebElement element = elements.get(0);
                if (!element.isDisplayed()) {
                    continue;
                }

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", element);
                af.hardWait(1);

                try {
                    element.click();
                } catch (Exception e) {
                    af.jsClick(element);
                }

                System.out.println("Clicked first availability button from results page using: " + locator);
                af.hardWait(1);
                return;
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Could not find See availability / Select date / Check availability on results page.");
    }

    public boolean isGalleryVisible() {
        try {
            List<WebElement> elements = driver.findElements(galleryVisibleMarker);
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
