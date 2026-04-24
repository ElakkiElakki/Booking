package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import util.AllFunctionalities;

public class FT_FlightOptionsPage {

	WebDriver driver;
	AllFunctionalities allFunc;

	public FT_FlightOptionsPage(WebDriver driver) {
		this.driver = driver;
		this.allFunc = new AllFunctionalities(driver);
	}

	// ================= SORT =================

	public void verifyCheapestSorting() {

		boolean isTabPresent = driver.findElements(By.id("TAB-CHEAPEST")).size() > 0;

		if (isTabPresent) {

			System.out.println("Using TAB layout");

			WebElement tab = allFunc.waitForVisible(By.id("TAB-CHEAPEST"));
			allFunc.jsClick(tab);

		} else {

			System.out.println("Using DROPDOWN layout");

			WebElement sort = allFunc.waitForVisible(By.xpath("//button[contains(.,'Sort by')]"));
			allFunc.click(sort);

			WebElement cheap = allFunc.waitForVisible(By.xpath("//button[.//div[text()='Cheapest']]"));
			allFunc.click(cheap);
		}

		By pricesLocator = By.xpath("//div[@data-testid='upt_price']");
		allFunc.waitForVisible(pricesLocator);

		List<WebElement> prices = allFunc.getElements(pricesLocator);

		int price1 = Integer.parseInt(prices.get(0).getText().replaceAll("[^0-9]", ""));
		int price2 = Integer.parseInt(prices.get(1).getText().replaceAll("[^0-9]", ""));

		System.out.println("Price 1: " + price1);
		System.out.println("Price 2: " + price2);

		Assert.assertTrue(price1 <= price2, "Sorting FAILED");

		System.out.println("Cheapest sorting PASSED");
	}

	// ================= VIEW DETAILS =================

	public void verifyFlightDetails() {

		By viewDetails = By.xpath("(//button[@data-testid='flight_card_bound_select_flight'])[1]");
		WebElement btn = allFunc.waitForVisible(viewDetails);
		allFunc.jsClick(btn);

		System.out.println("Clicked View Details");

		By baggage = By.xpath("//h2[text()='Baggage']");
		WebElement baggageElement = allFunc.waitForVisible(baggage);

		Assert.assertTrue(allFunc.isDisplayed(baggageElement),
				"Flight details popup FAILED — Baggage section not visible");

		System.out.println("Flight details popup PASSED");

		By closeBtn = By.xpath("//button[@aria-label='Close']");
		WebElement close = allFunc.waitForVisible(closeBtn);
		allFunc.click(close);

		System.out.println("Closed flight details popup");
	}

	// ================= FARE OPTIONS =================

	public void verifyFareOptions() {

		try {

			// Click Fare button
			By fareBtn = By.xpath("//button[@aria-controls='flights-fare-selector-0']");
			WebElement btn = allFunc.waitForVisible(fareBtn);
			allFunc.jsClick(btn);

			System.out.println("Clicked Fare Options");

			// 🔥 Wait for ANY fare card instead of carousel
			By fareCardsLocator = By.xpath("//div[@data-fare-card-row='title']");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(fareCardsLocator));

			List<WebElement> fareCards = allFunc.getElements(fareCardsLocator);

			if (fareCards.size() == 0) {
				throw new AssertionError("Fare options FAILED → No fare cards found");
			}

			System.out.println("Fare options displayed: " + fareCards.size());

			for (int i = 0; i < Math.min(3, fareCards.size()); i++) {
				System.out.println("Fare: " + fareCards.get(i).getText());
			}

		} catch (Exception e) {

			// 🔥 fallback (sometimes already visible / different UI)
			List<WebElement> fareCards = allFunc.getElements(By.xpath("//div[@data-fare-card-row='title']"));

			if (fareCards.size() == 0) {
				throw new AssertionError("Fare options FAILED — Not visible in any form");
			}

			System.out.println("Fare options already visible (fallback)");
		}
	}
	// ================= AIRLINE FILTER =================

	public void validateAirlineFilter() {

		By totalFlights = By.xpath("//div[@data-testid='search_filters_summary_results_number']");
		WebElement totalText = allFunc.waitForVisible(totalFlights);

		int beforeCount = Integer.parseInt(totalText.getText().replaceAll("[^0-9]", ""));
		System.out.println("Flights BEFORE filter: " + beforeCount);

		By airline = By.xpath("//div[text()='Etihad Airways']/ancestor::label");
		WebElement airlineCheckbox = allFunc.waitForVisible(airline);
		allFunc.jsClick(airlineCheckbox);

		System.out.println("Unchecked Etihad Airways");

		allFunc.hardWait(3); // small wait for update

		int afterCount = Integer.parseInt(driver.findElement(totalFlights).getText().replaceAll("[^0-9]", ""));

		System.out.println("Flights AFTER filter: " + afterCount);

		Assert.assertTrue(afterCount < beforeCount, "Airline filter FAILED");

		System.out.println("Airline filter PASSED");
	}

	// ================= TIME FILTER =================

	public void validateTimeFilter() {

		By filterCount = By.xpath("//span[@data-testid='flight_times_filter_v2_flight_time_departure_0_count']");
		WebElement countElement = allFunc.waitForVisible(filterCount);

		int expectedCount = Integer.parseInt(countElement.getText());
		System.out.println("Expected flights (filter): " + expectedCount);

		By checkbox = By.xpath("//div[text()='12:00 AM–5:59 AM']");
		WebElement cb = allFunc.waitForVisible(checkbox);
		allFunc.jsClick(cb);

		System.out.println("Time filter applied");

		By totalFlights = By.xpath("//div[@data-testid='search_filters_summary_results_number']");
		allFunc.hardWait(3);

		String text = driver.findElement(totalFlights).getText();
		int actualCount = Integer.parseInt(text.replaceAll("[^0-9]", ""));

		System.out.println("Actual flights (result): " + actualCount);

		Assert.assertEquals(actualCount, expectedCount, "Time filter FAILED");

		System.out.println("Time filter PASSED");
	}
}