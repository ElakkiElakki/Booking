package pages;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.*;

public class FT_EndToEndBookingPage {

	WebDriver driver;
	WebDriverWait wait;

	public FT_EndToEndBookingPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	}

	// ===== LOCATORS =====

	By oneWayRadio = By.xpath("//label[@for='search_type_option_ONEWAY']");
	By leavingFromBtn = By.xpath("//button[@data-ui-name='input_location_from_segment_0']");
	By removeDefaultCity = By.xpath("//button[@data-autocomplete-chip-idx='0']");
	By goingToBtn = By.xpath("//button[@data-ui-name='input_location_to_segment_0']");

	By dateBtn = By.xpath("//button[@data-ui-name='button_date_segment_0']");
	By date = By.xpath("//span[@data-date='2026-06-20']");

	By travellersBtn = By.xpath("//button[@data-ui-name='button_occupancy']");
	By childPlus = By.xpath("//button[@data-ui-name='button_occupancy_children_plus']");
	By childAgeDropdown = By.xpath("//select[@data-ui-name='select_occupancy_children_age_0']");
	By doneBtn = By.xpath("//button[@data-ui-name='button_occupancy_action_bar_done']");

	By searchBtn = By.xpath("//button[@data-ui-name='button_search_submit']");
	By inputField = By.xpath("//input[@data-ui-name='input_text_autocomplete']");
	By locationOption = By.xpath("(//li[@data-ui-name='locations_list_item'])[1]");

	By firstFlightCard = By.id("flightcard-0");
	By viewDetailsBtn = By.xpath("//div[@id='flightcard-0']//button[@data-testid='flight_card_bound_select_flight']");

	By ecoClassicContinue = By.xpath("//button[@data-testid='branded_fare_cta_1']");
	By nextBtn = By.xpath("(//button[.//span[text()='Next']])[1]");
//	By skipBtn = By.xpath("(//button[.//span[text()='Skip']])[1]");
	By skipBtn = By.xpath("//button[contains(.,'Skip')]" + " | //button[contains(.,'No thanks')]"
			+ " | //button[contains(.,'Continue')]" + " | //span[contains(.,'Skip')]/ancestor::button[1]");
	By contactEmail = By.name("booker.email");
	By countryCode = By.name("countryCode");
	By phoneNumber = By.name("number");

	By noFlexOption = By.xpath("//div[@data-testid='title' and text()='No flexibility']");

	// ===== BASIC =====

	public void selectOneWay() {
		WebElement oneWay = wait.until(ExpectedConditions.elementToBeClickable(oneWayRadio));
		if (!oneWay.isSelected()) {
			oneWay.click();
		}
	}

	// ===== DATATABLE: LOCATIONS =====

	public void enterLocations(String from1, String from2, String to) {

		waitAndClick(leavingFromBtn);
		waitAndClick(removeDefaultCity);

		waitAndType(inputField, from1);
		waitAndClick(locationOption);

		waitAndClick(leavingFromBtn);
		waitAndType(inputField, from2);
		waitAndClick(locationOption);

		waitAndClick(goingToBtn);
		waitAndType(inputField, to);
		waitAndClick(locationOption);
	}

	// ===== HARDCODED DATE + TRAVELLERS =====

	public void selectDateAndTravellers() {

		waitAndClick(dateBtn);
		waitAndClick(date);

		waitAndClick(travellersBtn);
		waitAndClick(childPlus);

		WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(childAgeDropdown));
		new Select(dropdown).selectByVisibleText("6");

		waitAndClick(doneBtn);
	}

	public void clickSearch() {
		waitAndClick(searchBtn);
	}

	public void verifyResults() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    By result = By.xpath(
	        "//div[contains(@data-testid,'flight_card')] | " +
	        "//button[contains(@data-testid,'flight_card')] | " +
	        "//button[contains(.,'Select')] | " +
	        "//button[contains(.,'View deal')] | " +
	        "//div[contains(@class,'FlightsTicket')]"
	    );

	    wait.until(ExpectedConditions.presenceOfElementLocated(result));

	    System.out.println("Flight search SUCCESS — results page loaded");
	}

	public void openFirstFlightDetails() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(firstFlightCard));
		wait.until(ExpectedConditions.elementToBeClickable(viewDetailsBtn)).click();
	}

	public void clickContinueFromDetails() {
		waitAndClick(By.xpath("(//button[.//span[text()='Continue']])[1]"));
	}

	public void selectTicketAndContinue() {
		WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(ecoClassicContinue));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	}

	// ===== TRAVELLER =====

	public void fillTravellerDetailsFromData(List<Map<String, String>> dataList) {

		List<WebElement> buttons = wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath("//button[contains(@aria-label,'Add this traveler')]")));

		int i = 0;

		for (WebElement btn : buttons) {

			Map<String, String> data = dataList.get(i);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

			By firstNameLocator = By.xpath("//input[contains(@name,'firstName')]");

			WebElement fn = wait.until(ExpectedConditions.elementToBeClickable(firstNameLocator));
			fn.sendKeys(Keys.CONTROL + "a");
			fn.sendKeys(Keys.DELETE);
			fn.sendKeys(data.get("firstName"));

			WebElement ln = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'lastName')]")));
			ln.sendKeys(Keys.CONTROL + "a");
			ln.sendKeys(Keys.DELETE);
			ln.sendKeys(data.get("lastName"));

			new Select(
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[contains(@name,'gender')]"))))
					.selectByVisibleText(data.get("gender"));

			// CHILD
			if (i == 1) {
				new Select(wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[contains(@name,'month')]"))))
						.selectByVisibleText("June");

				WebElement day = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'day')]")));
				day.sendKeys(Keys.CONTROL + "a");
				day.sendKeys(Keys.DELETE);
				day.sendKeys("10");

				WebElement year = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'year')]")));
				year.sendKeys(Keys.CONTROL + "a");
				year.sendKeys(Keys.DELETE);
				year.sendKeys("2018");
			}

			waitAndClick(By.xpath("//button[.//span[text()='Done']]"));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(firstNameLocator));

			i++;
		}
	}

	// ===== CONTACT =====

	public void fillContactDetailsFromData(String email, String country, String phone) {

		WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(contactEmail));
		emailField.sendKeys(Keys.CONTROL + "a");
		emailField.sendKeys(Keys.DELETE);
		emailField.sendKeys(email);

		WebElement codeDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(countryCode));
		new Select(codeDropdown).selectByVisibleText(country);

		WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumber));
		phoneField.sendKeys(Keys.CONTROL + "a");
		phoneField.sendKeys(Keys.DELETE);
		phoneField.sendKeys(phone);

		waitAndClick(nextBtn);
	}

	// ===== FINAL STEPS =====

	public void selectTicketType() {
		WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(noFlexOption));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
	}

	public void clickNextAfterTicket() {
		WebElement next = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[.//span[text()='Next']])[last()]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", next);
	}

	public void skipSeatSelection() {

		System.out.println("Handling seat selection page...");

		try {
			// ✅ Wait until we are ACTUALLY on seat page
			wait.until(ExpectedConditions.urlContains("seats"));

			// ✅ Strong locator (matches your real HTML)
			By skipLocator = By.xpath("//button[.//span[text()='Skip']]");

			WebElement skip = wait.until(ExpectedConditions.elementToBeClickable(skipLocator));

			// scroll (important for Booking UI)
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", skip);

			// click
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", skip);

			System.out.println("✅ Clicked Skip button");

		} catch (Exception e) {

			System.out.println("⚠️ Skip not found — trying fallback");

			try {
				// fallback → sometimes Continue is used instead
				By continueBtn = By.xpath("//button[.//span[text()='Continue']]");
				WebElement cont = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", cont);

				System.out.println("✅ Clicked Continue instead of Skip");

			} catch (Exception ex) {
				throw new RuntimeException("❌ Neither Skip nor Continue worked on seat page");
			}
		}

		// ✅ WAIT FOR NEXT PAGE (VERY IMPORTANT)
		wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("seats")));

		System.out.println("Moved past seat selection page");
	}

	public void verifyPaymentPage() {

		wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("payment"),
				ExpectedConditions.urlContains("checkout"), ExpectedConditions.urlContains("extras")));

		System.out.println("✅ Reached next booking stage: " + driver.getCurrentUrl());
	}
	// ===== COMMON =====

	public WebElement waitAndClick(By locator) {
		WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		wait.until(ExpectedConditions.elementToBeClickable(el));

		try {
			el.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
		}

		return el;
	}
	public void selectTravelProtectionIfPresent() {
	    try {
	        By travelProtectionCard = By.xpath(
	            "//div[@data-testid='title' and normalize-space()='Travel protection']/ancestor::label[1]" +
	            " | //div[contains(normalize-space(),'Travel protection')]/ancestor::label[1]"
	        );

	        List<WebElement> options = driver.findElements(travelProtectionCard);

	        if (options.isEmpty()) {
	            System.out.println("Travel protection section not present. Skipping.");
	            return;
	        }

	        WebElement option = options.get(0);

	        ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});", option
	        );

	        ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].click();", option
	        );

	        System.out.println("Clicked Travel protection radio card");

	    } catch (Exception e) {
	        System.out.println("Travel protection not clickable. Skipping.");
	    }
	}

	public WebElement waitAndType(By locator, String text) {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		el.clear();
		el.sendKeys(text);
		return el;
	}
}