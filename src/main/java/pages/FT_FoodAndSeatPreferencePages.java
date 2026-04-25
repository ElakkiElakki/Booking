package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class FT_FoodAndSeatPreferencePages {

	WebDriver driver;
	WebDriverWait wait;

	public FT_FoodAndSeatPreferencePages(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

// ================= LOCATORS =================

// FOOD
	By meal1 = By.id("meal_choice_1");
	By meal2 = By.id("meal_choice_2");

// PRICE
	By adultDropdown = By.xpath("//div[@data-testid='upb_title-flight_adult']");
	By childDropdown = By.xpath("//div[@data-testid='upb_title-flight_child']");

// FLEX
	By nextBtn = By.xpath("//button[.//span[text()='Next']]");
	By flexibleOption = By.xpath("//input[@name='ticket-type' and @value='flexible']");

// SEATS
	By availableSeats = By.xpath("//button[contains(@data-testid,'seat_') and not(.//span[text()='X'])]");

// TAB
	By nextTab = By.xpath("(//button[contains(@data-testid,'seat_map_tab')])[2]");

// PAYMENT
	By checkAndPay = By.xpath("//button[.//span[text()='Check and pay']]");
// ================= HELPERS =================

	private void jsClick(WebElement el) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
	}

// ================= FOOD =================

	public void selectFood() {
		new Select(wait.until(ExpectedConditions.elementToBeClickable(meal1))).selectByVisibleText("Kosher · Free");

		new Select(wait.until(ExpectedConditions.elementToBeClickable(meal2))).selectByVisibleText("Vegetarian · Free");

		System.out.println("Food selected");
	}

// ================= PRICE =================

	public void expandPriceDropdown() {
		wait.until(ExpectedConditions.elementToBeClickable(adultDropdown)).click();
		wait.until(ExpectedConditions.elementToBeClickable(childDropdown)).click();
	}

// ================= FLEX =================

	public void waitForFlexPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(nextBtn));
	}

	public void selectFlexibleOption() {
		WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(flexibleOption));
		jsClick(el);
	}

	public void clickNext() {
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
		jsClick(btn);
	}

// ================= SEATS =================
	public void selectSeatsForBothSegments() {

		// ========== SEGMENT 1 ==========
		System.out.println("Selecting seats for Chennai → Manama");

		clickTwoAvailableSeats();

		waitForUI();

		// ========== SWITCH TAB ==========
		By nextTab = By.xpath("(//button[contains(@data-testid,'seat_map_tab')])[2]");
		wait.until(ExpectedConditions.elementToBeClickable(nextTab)).click();

		System.out.println("Switched to Manama → Paris");

		waitForUI();

		// ========== SEGMENT 2 ==========
		System.out.println("Selecting seats for Manama → Paris");

		clickTwoAvailableSeats();
	}

	private void clickTwoAvailableSeats() {

	    By seatsLocator = By.xpath(
	        "//button[not(@disabled) and (" +
	        "contains(@data-testid,'seat') or " +
	        "contains(@aria-label,'seat') or " +
	        "contains(@aria-label,'Seat') or " +
	        "contains(@class,'seat')" +
	        ")]"
	    );

	    java.util.List<WebElement> seats = wait.until(
	        ExpectedConditions.presenceOfAllElementsLocatedBy(seatsLocator)
	    );

	    int clicked = 0;

	    for (WebElement seat : seats) {
	        try {
	            if (seat.isDisplayed() && seat.isEnabled()) {

	                ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].scrollIntoView({block:'center'});", seat
	                );

	                String label = seat.getAttribute("aria-label");

	                ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].click();", seat
	                );

	                System.out.println("Selected seat → " + label);

	                clicked++;
	                waitForUI();

	                if (clicked == 2) {
	                    break;
	                }
	            }
	        } catch (Exception ignored) {
	        }
	    }

	    if (clicked < 2) {
	        throw new RuntimeException("Could not select two available seats.");
	    }
	}
	private void waitForUI() {
		try {
			Thread.sleep(1200);
		} catch (Exception e) {
		}
	}

	public void switchToNextTab() {
		WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(nextTab));
		jsClick(tab);
		System.out.println("Switched to next flight tab");
	}

// ================= PAYMENT =================

	public void clickCheckOrSkip() {
		selectNoTravelProtection();
		By checkAndPay = By.xpath("//button[.//span[text()='Check and pay']]");
		By skip = By.xpath("//button[.//span[text()='Skip']]");

		try {
			WebElement checkBtn = wait.until(ExpectedConditions.presenceOfElementLocated(checkAndPay));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkBtn);

			wait.until(ExpectedConditions.elementToBeClickable(checkBtn));

			checkBtn.click();

			System.out.println("Clicked Check and Pay");

		} catch (Exception e) {

			System.out.println("Check and Pay not found → trying Skip");

			WebElement skipBtn = wait.until(ExpectedConditions.elementToBeClickable(skip));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", skipBtn);

			skipBtn.click();

			System.out.println("Clicked Skip instead");
		}
	}
	
	public void selectNoTravelProtection() {

	    By card = By.xpath(
	        "//div[@data-testid='subtitle_0' and contains(text(),'€0.00')]/ancestor::label[1]"
	    );

	    WebElement option = wait.until(
	        ExpectedConditions.elementToBeClickable(card)
	    );

	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView({block:'center'});", option
	    );

	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].click();", option
	    );

	    System.out.println("Clicked No travel protection card");
	}
	public void verifyPaymentPage() {
		wait.until(driver -> driver.getCurrentUrl().contains("payment"));

		if (!driver.getCurrentUrl().contains("payment")) {
			throw new AssertionError("❌ Not on payment page");
		}

		System.out.println("✅ Payment page reached");
	}
}
