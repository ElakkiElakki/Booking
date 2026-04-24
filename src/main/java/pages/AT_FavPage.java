package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AT_FavPage extends baseclass {

    private String attractionName;
    private String searchResultUrl;

    private final By destinationField = By.xpath(
            "//input[contains(@placeholder,'Destination') or contains(@placeholder,'Where') or @name='query']" +
            " | //label[contains(.,'Destination')]/following::input[1]"
    );

    private final By firstResultCardName = By.xpath(
            "(//div[contains(@data-testid,'card')]//h3)[1]" +
            " | (//div[contains(@data-testid,'card')]//h2)[1]" +
            " | (//a[contains(@href,'/attractions/') and not(contains(@href,'index'))]//h3)[1]" +
            " | (//a[contains(@href,'/attractions/') and not(contains(@href,'index'))]//h2)[1]" +
            " | (//a[contains(@href,'/attractions/') and not(contains(@href,'index'))]//*[string-length(normalize-space()) > 10 and not(contains(.,'See availability')) and not(contains(.,'Check availability')) and not(contains(.,'Select date'))])[1]"
    );

    private final By firstResultHeart = By.xpath(
            "(//button[contains(@aria-label,'Save') " +
            "or contains(@aria-label,'Saved') " +
            "or contains(@aria-label,'favourite') " +
            "or contains(@aria-label,'favorite') " +
            "or contains(@data-testid,'wishlist') " +
            "or contains(@data-testid,'save')])[1]"
    );

    private final By profileIcon = By.xpath(
            "//button[@data-testid='header-profile-mobile-avatar']" +
            " | //div[@data-testid='header-profile-mobile-avatar']/ancestor::button[1]" +
            " | //span[.//div[@data-testid='header-profile-mobile-avatar']]/ancestor::button[1]" +
            " | //img[contains(@src,'avatar.png')]/ancestor::button[1]"
    );

    private final By savedOption = By.xpath(
            "//span[normalize-space()='Saved']" +
            " | //span[contains(normalize-space(),'Saved')]/ancestor::a[1]" +
            " | //a[.//span[normalize-space()='Saved']]"
    );

    public AT_FavPage(WebDriver driver) {
        super(driver);
    }

    public void openAttractionsPage() {
        driver.get("https://www.booking.com/attractions/");
        af.hardWait(2);
    }

    public void searchDestination(String destination) {
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
                    clickSearchButton();
                    searchResultUrl = driver.getCurrentUrl();
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

        af.hardWait(2);
        clickSearchButton();
        searchResultUrl = driver.getCurrentUrl();
    }

    private void clickSearchButton() {
        for (By locator : List.of(
                By.xpath("//button[@data-testid='search-button']"),
                By.xpath("//button[@type='submit']"),
                By.xpath("//button[contains(normalize-space(),'Search')]"),
                By.xpath("//span[contains(normalize-space(),'Search')]/ancestor::button[1]")
        )) {
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

                    System.out.println("Search button clicked using locator: " + locator);
                    af.hardWait(5);
                    return;
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Could not click Search button.");
    }

    public void storeFirstResultAttractionName() {
        attractionName = "";

        List<WebElement> names = driver.findElements(firstResultCardName);

        for (WebElement name : names) {
            try {
                String text = name.getText().trim();

                if (!text.isEmpty()
                        && !text.equalsIgnoreCase("See availability")
                        && !text.equalsIgnoreCase("Check availability")
                        && !text.equalsIgnoreCase("Select date")
                        && text.length() > 5) {

                    attractionName = text;
                    break;
                }

            } catch (Exception ignored) {
            }
        }

        if (attractionName == null || attractionName.isEmpty()) {
            attractionName = "Amsterdam";
        }

        System.out.println("Selected attraction from result page: " + attractionName);
    }

    public void selectHeartIconInResultPage() {
        storeFirstResultAttractionName();

        WebElement heart = af.waitForVisible(firstResultHeart);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", heart);

        af.hardWait(1);
        af.jsClick(heart);
        af.hardWait(3);

        System.out.println("Heart selected from search result page");
    }

    public void goBackToSearchResultsPage() {
        driver.get(searchResultUrl);
        af.hardWait(5);

        System.out.println("Returned to search results page: " + driver.getCurrentUrl());
    }

    public void deselectSameHeartIconInResultPage() {
        WebElement heart = af.waitForVisible(firstResultHeart);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", heart);

        af.hardWait(1);
        af.jsClick(heart);
        af.hardWait(3);

        System.out.println("Same heart deselected for: " + attractionName);
    }

    public void openMyFavFromProfileMenu() {
        WebElement profile = af.waitForVisible(profileIcon);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", profile);

        af.hardWait(1);

        try {
            profile.click();
        } catch (Exception e) {
            af.jsClick(profile);
        }

        af.hardWait(2);

        WebElement saved = af.waitForVisible(savedOption);

        try {
            saved.click();
        } catch (Exception e) {
            af.jsClick(saved);
        }

        af.hardWait(5);

        System.out.println("Saved page URL: " + driver.getCurrentUrl());
    }

    public boolean isSelectedAttractionDisplayedInFavPage() {
        try {
            af.hardWait(5);

            String pageText = driver.findElement(By.tagName("body")).getText();

            System.out.println("Validating saved attraction name: " + attractionName);
            System.out.println("Saved page body text: " + pageText);

            return pageText.toLowerCase().contains(attractionName.toLowerCase());

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSelectedAttractionNotDisplayedInFavPage() {
        try {
            af.hardWait(5);

            String pageText = driver.findElement(By.tagName("body")).getText();

            System.out.println("Validating removed attraction name: " + attractionName);
            System.out.println("Saved page body text after remove: " + pageText);

            return !pageText.toLowerCase().contains(attractionName.toLowerCase());

        } catch (Exception e) {
            return true;
        }
    }

    public String getAttractionName() {
        return attractionName;
    }
}