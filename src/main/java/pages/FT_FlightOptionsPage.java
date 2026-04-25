package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import util.AllFunctionalities;

public class FT_FlightOptionsPage {

    WebDriver driver;
    AllFunctionalities allFunc;
    WebDriverWait wait;

    public FT_FlightOptionsPage(WebDriver driver) {
        this.driver = driver;
        this.allFunc = new AllFunctionalities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ================= LOCATORS =================

    // SORT
    By cheapestTab = By.id("TAB-CHEAPEST");
    By sortButton = By.xpath("//button[contains(.,'Sort by')]");
    By cheapestOption = By.xpath("//button[.//div[text()='Cheapest']]");
    By pricesLocator = By.xpath("//div[@data-testid='upt_price']");

    // VIEW DETAILS
    By viewDetailsBtn = By.xpath("(//button[@data-testid='flight_card_bound_select_flight'])[1]");
    By baggageSection = By.xpath("//h2[text()='Baggage']");
    By closePopupBtn = By.xpath("//button[@aria-label='Close']");

    // FARE OPTIONS
    By fareBtn = By.xpath("//button[contains(@aria-controls,'fare')]");

    // robust fare locators
    By fareTitlesPrimary = By.xpath("//div[@data-fare-card-row='title']");
    By fareTitlesAlt = By.xpath("//div[contains(text(),'Economy') or contains(text(),'Flex') or contains(text(),'Smart')]");
    By farePrices = By.xpath("//div[@data-testid='upt_price']");

    // AIRLINE FILTER
    By totalFlightsText = By.xpath("//div[@data-testid='search_filters_summary_results_number']");
    By airlineCheckbox = By.xpath("//div[text()='Etihad Airways']/ancestor::label");

    // TIME FILTER
    By timeFilterCount = By.xpath("//span[@data-testid='flight_times_filter_v2_flight_time_departure_0_count']");
    By timeCheckbox = By.xpath("//div[text()='12:00 AM–5:59 AM']");

    // ONE WAY
    By oneWayOption = By.xpath("//label[@for='search_type_option_ONEWAY']");

    // ================= SORT =================

    public void verifyCheapestSorting() {

        if (driver.findElements(cheapestTab).size() > 0) {

            WebElement tab = allFunc.waitForVisible(cheapestTab);
            allFunc.jsClick(tab);

        } else {

            WebElement sort = allFunc.waitForVisible(sortButton);
            allFunc.click(sort);

            WebElement cheap = allFunc.waitForVisible(cheapestOption);
            allFunc.click(cheap);
        }

        allFunc.waitForVisible(pricesLocator);

        List<WebElement> prices = allFunc.getElements(pricesLocator);

        int price1 = Integer.parseInt(prices.get(0).getText().replaceAll("[^0-9]", ""));
        int price2 = Integer.parseInt(prices.get(1).getText().replaceAll("[^0-9]", ""));

        Assert.assertTrue(price1 <= price2, "Sorting FAILED");
    }

    // ================= VIEW DETAILS =================

    public void verifyFlightDetails() {

        WebElement btn = allFunc.waitForVisible(viewDetailsBtn);
        allFunc.jsClick(btn);

        WebElement baggageElement = allFunc.waitForVisible(baggageSection);

        Assert.assertTrue(allFunc.isDisplayed(baggageElement),
                "Flight details popup FAILED");

        WebElement close = allFunc.waitForVisible(closePopupBtn);
        allFunc.click(close);
    }

    // ================= FARE OPTIONS =================

    public void verifyFareOptions() {

        WebElement btn = allFunc.waitForVisible(fareBtn);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        allFunc.jsClick(btn);

        allFunc.hardWait(2);

        // wait for ANY fare content instead of container
        wait.until(driver ->
                driver.findElements(fareTitlesPrimary).size() > 0 ||
                driver.findElements(fareTitlesAlt).size() > 0 ||
                driver.findElements(farePrices).size() > 1
        );

        List<WebElement> fares = driver.findElements(fareTitlesPrimary);

        if (fares.size() == 0) {
            fares = driver.findElements(fareTitlesAlt);
        }

        if (fares.size() == 0) {
            fares = driver.findElements(farePrices);
        }

        if (fares.size() == 0) {
            throw new AssertionError("Fare options FAILED — nothing loaded");
        }

        for (WebElement fare : fares) {
            System.out.println("Fare: " + fare.getText());
        }
    }

    // ================= AIRLINE FILTER =================

    public void validateAirlineFilter() {

        int beforeCount = Integer.parseInt(
                allFunc.waitForVisible(totalFlightsText).getText().replaceAll("[^0-9]", "")
        );

        WebElement airline = allFunc.waitForVisible(airlineCheckbox);
        allFunc.jsClick(airline);

        allFunc.hardWait(3);

        int afterCount = Integer.parseInt(
                driver.findElement(totalFlightsText).getText().replaceAll("[^0-9]", "")
        );

        Assert.assertTrue(afterCount < beforeCount, "Airline filter FAILED");
    }

    // ================= TIME FILTER =================

    public void validateTimeFilter() {

        int expectedCount = Integer.parseInt(
                allFunc.waitForVisible(timeFilterCount).getText()
        );

        WebElement cb = allFunc.waitForVisible(timeCheckbox);
        allFunc.jsClick(cb);

        allFunc.hardWait(3);

        int actualCount = Integer.parseInt(
                driver.findElement(totalFlightsText).getText().replaceAll("[^0-9]", "")
        );

        Assert.assertEquals(actualCount, expectedCount, "Time filter FAILED");
    }

    // ================= ONE WAY =================

    public void selectOneWay() {

        WebElement element = allFunc.waitForVisible(oneWayOption);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}