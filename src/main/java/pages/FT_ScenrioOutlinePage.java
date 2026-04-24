package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class FT_ScenrioOutlinePage {

	WebDriver driver;
	WebDriverWait wait;

	public FT_ScenrioOutlinePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// 🔹 LOCATORS
	By flightsTab = By.id("flights");

	By leavingFromBtn = By.xpath("//button[@data-ui-name='input_location_from_segment_0']");
	By goingToBtn = By.xpath("//button[@data-ui-name='input_location_to_segment_0']");
	By removeChip = By.xpath("//button[@data-autocomplete-chip-idx='0']");

	By input = By.xpath("//input[@data-ui-name='input_text_autocomplete']");
	By firstOption = By.xpath("//li[@data-ui-name='locations_list_item']");

	By searchBtn = By.xpath("//button[@data-ui-name='button_search_submit']");

	// ================= SETUP =================

	public void openFlights() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(flightsTab));
		wait.until(ExpectedConditions.elementToBeClickable(flightsTab)).click();

		// 🔥 wait until fields are ready
		wait.until(ExpectedConditions.visibilityOfElementLocated(leavingFromBtn));
	}

	// ================= DEPARTURE =================

	public void enterDeparture(String city) {

		if (city == null || city.isEmpty()) {
			clearDeparture(); // 🔥 IMPORTANT
			return;
		}

		wait.until(ExpectedConditions.elementToBeClickable(leavingFromBtn)).click();

		WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(input));
		box.clear();
		box.sendKeys(city);

		wait.until(ExpectedConditions.elementToBeClickable(firstOption)).click();
	}

	// ================= DESTINATION =================

	public void enterDestination(String city) {

		if (city == null || city.isEmpty()) {
			clearDestination(); // 🔥 IMPORTANT
			return;
		}

		wait.until(ExpectedConditions.elementToBeClickable(goingToBtn)).click();

		WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(input));
		box.clear();
		box.sendKeys(city);

		wait.until(ExpectedConditions.elementToBeClickable(firstOption)).click();
	}

	// ================= SEARCH =================

	public void clickSearch() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
	}

	// ================= VALIDATIONS =================

	public void validateResult(String expected) {

		switch (expected) {

		case "missing_departure":
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(text(),'airport') or contains(text(),'city')]")));
			System.out.println("✅ Missing departure validated");
			break;

		case "missing_destination":
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(@class,'FlyAnywhereCardsListItemDesktop')]")));
			System.out.println("✅ Missing destination validated");
			break;

		case "success":
			wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.id("TAB-BEST")),
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Sort by')]"))));
			System.out.println("✅ Search success");
			break;

		default:
			throw new RuntimeException("Invalid expected: " + expected);
		}
	}

	public void clearDeparture() {
		wait.until(ExpectedConditions.elementToBeClickable(leavingFromBtn)).click();

		try {
			wait.until(ExpectedConditions.elementToBeClickable(removeChip)).click();
		} catch (Exception e) {
			System.out.println("No default departure to clear");
		}
	}

	public void clearDestination() {
		wait.until(ExpectedConditions.elementToBeClickable(goingToBtn)).click();

		try {
			wait.until(ExpectedConditions.elementToBeClickable(removeChip)).click();
		} catch (Exception e) {
			System.out.println("No default destination to clear");
		}
	}
}