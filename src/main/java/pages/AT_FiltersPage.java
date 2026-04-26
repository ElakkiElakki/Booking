package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AT_FiltersPage extends baseclass {

    private final By destinationField = By.xpath(
            "//input[contains(@placeholder,'Destination') or contains(@placeholder,'Where') or @name='query']" +
            " | //label[contains(.,'Destination')]/following::input[1]"
    );

    private final By searchButton = By.xpath(
            "//button[@data-testid='search-button']" +
            " | //button[@type='submit']" +
            " | //button[contains(.,'Search')]" +
            " | //span[contains(.,'Search')]/ancestor::button"
    );

    private final By resultsHeader = By.xpath("//h1 | //main");

    public AT_FiltersPage(WebDriver driver) {
        super(driver);
    }

    public void openAttractionsPage() {
        driver.get("https://www.booking.com/attractions/");
        af.hardWait(1);
    }

    public void enterDestination(String destination) {
        WebElement destinationBox = af.waitForVisible(destinationField);
        destinationBox.click();
        destinationBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        destinationBox.sendKeys(Keys.DELETE);
        destinationBox.sendKeys(destination);

        af.hardWait(1);
        destinationBox.sendKeys(Keys.ARROW_DOWN);
        af.hardWait(1);
        destinationBox.sendKeys(Keys.ENTER);
        af.hardWait(1);
    }

    public void clickSearch() {
        WebElement button = af.waitForVisible(searchButton);
        try {
            button.click();
        } catch (Exception e) {
            af.jsClick(button);
        }
        af.hardWait(1);
    }

    public boolean isResultsPageDisplayed() {
        try {
            return !driver.findElements(resultsHeader).isEmpty()
                    && driver.getCurrentUrl().contains("/attractions/");
        } catch (Exception e) {
            return false;
        }
    }

    public void selectCategoryFilter(String category) {
        WebElement element = findClickableFilter(category);
        scrollAndClick(element);
        System.out.println("Category filter clicked: " + category);
    }

    public void selectReviewScore(String reviewScore) {
        WebElement element = findClickableReviewFilter(reviewScore);
        scrollAndClick(element);
        System.out.println("Review score filter clicked: " + reviewScore);
    }

    public void selectTimeOfDay(String timeOfDay) {
        WebElement element = findClickableFilter(timeOfDay);
        scrollAndClick(element);
        System.out.println("Time of day filter clicked: " + timeOfDay);
    }

    public void clickLowestPriceSort() {
        WebElement element = findClickableSort("Lowest price");
        scrollAndClick(element);
        System.out.println("Lowest price sort clicked.");
    }

    public boolean isFilterSelected(String filterName) {
        try {
            return driver.getPageSource().toLowerCase().contains(filterName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areMultipleFiltersSelected() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();

            boolean categoryPresent =
                    pageSource.contains("nature & outdoor") || pageSource.contains("nature");

            boolean reviewPresent =
                    pageSource.contains("4 and up")
                    || pageSource.contains("4.0 and up")
                    || pageSource.contains("4+");

            boolean timePresent =
                    pageSource.contains("afternoon");

            System.out.println("Category present: " + categoryPresent);
            System.out.println("Review present: " + reviewPresent);
            System.out.println("Time present: " + timePresent);

            return categoryPresent && reviewPresent && timePresent;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLowestPriceSelected() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains("lowest price");
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement findClickableFilter(String visibleText) {
        By[] locators = new By[] {
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::label[1]"),
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::*[@role='checkbox'][1]"),
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::button[1]"),
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::div[1]"),
                By.xpath("//*[contains(normalize-space(),'" + visibleText + "')]")
        };

        for (By locator : locators) {
            try {
                WebElement el = af.waitForVisible(locator);
                if (el != null && el.isDisplayed()) {
                    return el;
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Filter not found: " + visibleText);
    }

    private WebElement findClickableReviewFilter(String reviewScore) {
        By[] locators = new By[] {
                By.xpath("//*[contains(normalize-space(),'4 and up')]/ancestor::label[1]"),
                By.xpath("//*[contains(normalize-space(),'4.0 and up')]/ancestor::label[1]"),
                By.xpath("//*[contains(normalize-space(),'4+')]/ancestor::label[1]"),
                By.xpath("//*[contains(normalize-space(),'4 and up')]/ancestor::*[@role='checkbox'][1]"),
                By.xpath("//*[contains(normalize-space(),'4.0 and up')]/ancestor::*[@role='checkbox'][1]"),
                By.xpath("//*[contains(normalize-space(),'4+')]/ancestor::*[@role='checkbox'][1]"),
                By.xpath("//*[contains(normalize-space(),'" + reviewScore + "')]")
        };

        for (By locator : locators) {
            try {
                WebElement el = af.waitForVisible(locator);
                if (el != null && el.isDisplayed()) {
                    return el;
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Review score filter not found: " + reviewScore);
    }

    private WebElement findClickableSort(String visibleText) {
        By[] locators = new By[] {
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::button[1]"),
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::label[1]"),
                By.xpath("//*[normalize-space()='" + visibleText + "']/ancestor::div[1]"),
                By.xpath("//*[contains(normalize-space(),'" + visibleText + "')]")
        };

        for (By locator : locators) {
            try {
                WebElement el = af.waitForVisible(locator);
                if (el != null && el.isDisplayed()) {
                    return el;
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Sort option not found: " + visibleText);
    }

    private void scrollAndClick(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", element
            );
            af.hardWait(1);
            element.click();
        } catch (Exception e) {
            af.jsClick(element);
        }
    }
}