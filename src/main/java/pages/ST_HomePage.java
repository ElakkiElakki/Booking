package pages;

import base.baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ST_HomePage extends baseclass {

	WebDriverWait wait;

	// ── Page Factory elements ─────────────────────────────────────────────
	@FindBy(name = "ss")
	WebElement searchBox;

	@FindBy(css = "button[type='submit']")
	WebElement searchBtn;

	@FindBy(css = "[data-testid='searchbox-dates-container']")
	WebElement dateContainer;

	// ✅ Guests container
	@FindBy(css = "[data-testid='occupancy-config']")
	WebElement guestsContainer;

	// ✅ Done button in guests popup
	@FindBy(css = "button[data-testid='occupancy-popup-done-cta']")
	WebElement guestsDoneBtn;

	public ST_HomePage(WebDriver driver) {
		super(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// ── Enter destination ─────────────────────────────────────────────────
//	public void enterDestination(String destination) throws InterruptedException {
//		searchBox.click();
//		Thread.sleep(300);
//		searchBox.clear();
//
//		for (char c : destination.toCharArray()) {
//			searchBox.sendKeys(String.valueOf(c));
//			Thread.sleep(80);
//		}
//		Thread.sleep(2000);
//
//		// Click first autocomplete suggestion
//		try {
//			WebElement suggestion = wait.until(ExpectedConditions
//					.visibilityOfElementLocated(By.cssSelector("[data-testid='autocomplete-result']")));
//			System.out.println("Suggestion: " + suggestion.getText());
//			suggestion.click();
//		} catch (Exception e) {
//			System.out.println("No autocomplete");
//		}
//	}
	public void enterDestination(String destination) throws InterruptedException {
	    searchBox.click();
	    searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    searchBox.sendKeys(Keys.DELETE);

	    searchBox.sendKeys(destination);
	    Thread.sleep(1000);

	    try {
	        WebElement suggestion = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.cssSelector("[data-testid='autocomplete-result']")));
	        System.out.println("Suggestion: " + suggestion.getText());
	        suggestion.click();
	    } catch (Exception e) {
	        System.out.println("No autocomplete");
	    }
	}

	// ── Clear destination ─────────────────────────────────────────────────
//	public void clearDestination() {
//		searchBox.clear();
//		searchBox.sendKeys(Keys.TAB);
//	}
	public void clearDestination() {
	    searchBox.click();
	    searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    searchBox.sendKeys(Keys.DELETE);
	    searchBox.sendKeys(Keys.TAB);
	}

	// ── Select checkin date ───────────────────────────────────────────────
	public boolean selectCheckinDate(String date) throws InterruptedException {
		try {
// ✅ Step 1 — Click date field
			WebElement dateField = driver.findElement(By.cssSelector("[data-testid='date-display-field-start']"));
			dateField.click();
			System.out.println("Calendar opened ✅");
			Thread.sleep(2000); // Wait longer

// ✅ DEBUG — Print all span[data-date] found on calendar
			List<WebElement> allDates = driver.findElements(By.cssSelector("span[data-date]"));
			System.out.println("Total date spans found: " + allDates.size());

// Print first 5 dates visible
			int count = 0;
			for (WebElement d : allDates) {
				if (count < 5) {
					System.out.println(
							"Date on calendar: " + d.getAttribute("data-date") + " visible: " + d.isDisplayed());
					count++;
				}
			}

// ✅ Step 2 — Navigate to correct month
			navigateCalendarToDate(date);

// ✅ Step 3 — Find target date
			List<WebElement> dateCells = driver.findElements(By.cssSelector("span[data-date='" + date + "']"));
			System.out.println("Target date cells: " + dateCells.size());

			if (dateCells.isEmpty()) {
				System.out.println("Date not found — past date ✅");
				driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
				return false;
			}

			WebElement dateCell = dateCells.get(0);
			String ariaDisabled = dateCell.getAttribute("aria-disabled");
			System.out.println("aria-disabled: " + ariaDisabled);

			if ("true".equals(ariaDisabled)) {
				System.out.println("Past date disabled ✅");
				driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
				return false;
			}

// ✅ Step 4 — Try multiple click methods
			try {
// Method 1 — Normal click
				dateCell.click();
				System.out.println("Normal click done ✅");
			} catch (Exception e1) {
				try {
// Method 2 — JS click
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateCell);
					System.out.println("JS click done ✅");
				} catch (Exception e2) {
// Method 3 — Actions click
					new org.openqa.selenium.interactions.Actions(driver).moveToElement(dateCell).click().perform();
					System.out.println("Actions click done ✅");
				}
			}
			Thread.sleep(1000);

// ✅ DEBUG — Check if date was selected
			String classAfter = dateCell.getAttribute("class");
			System.out.println("Class after click: " + classAfter);

// ✅ Step 5 — Select checkout
			selectCheckoutDate(date);

			return true;

		} catch (Exception e) {
			System.out.println("selectCheckinDate: " + e.getMessage());
			return false;
		}
	}

	// ── Select checkout date ──────────────────────────────────────────────
	private void selectCheckoutDate(String checkinDate) throws InterruptedException {
		try {
			// ✅ Calculate checkout = checkin + 7 days
			String[] parts = checkinDate.split("-");
			int year = Integer.parseInt(parts[0]);
			int month = Integer.parseInt(parts[1]);
			int day = Integer.parseInt(parts[2]) + 7;

			// Handle month overflow
			int[] daysInMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			if (day > daysInMonth[month]) {
				day = day - daysInMonth[month];
				month = month + 1;
				if (month > 12) {
					month = 1;
					year = year + 1;
				}
			}

			String checkoutDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

			System.out.println("Checkout date: " + checkoutDate);

			// ✅ Navigate if needed
			navigateCalendarToDate(checkoutDate);

			// ✅ Click checkout
			WebElement checkout = driver.findElement(By.cssSelector("span[data-date='" + checkoutDate + "']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkout);
			System.out.println("Checkout clicked ✅");
			Thread.sleep(500);

		} catch (Exception e) {
			System.out.println("selectCheckoutDate: " + e.getMessage());
		}
	}

	// ── Navigate calendar to correct month ───────────────────────────────
	private void navigateCalendarToDate(String targetDate) throws InterruptedException {

		String[] parts = targetDate.split("-");
		int targetYear = Integer.parseInt(parts[0]);
		int targetMonth = Integer.parseInt(parts[1]);

		String[] monthNames = { "", "January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December" };

		for (int attempt = 0; attempt < 24; attempt++) {

// ✅ Check header
			try {
				List<WebElement> headers = driver.findElements(By.cssSelector("h3"));
				for (WebElement h : headers) {
					String text = h.getText().trim();
					if (text.contains(monthNames[targetMonth]) && text.contains(String.valueOf(targetYear))) {
						System.out.println("Month found ✅");
						return;
					}
				}
			} catch (Exception ignored) {
			}

// ✅ Check span
			List<WebElement> found = driver.findElements(By.cssSelector("span[data-date='" + targetDate + "']"));
			if (!found.isEmpty() && found.get(0).isDisplayed()) {
				System.out.println("Date visible ✅");
				return;
			}

// ✅ Decide direction
			try {
				List<WebElement> headers = driver.findElements(By.cssSelector("h3"));

				int currentYear = targetYear;
				int currentMonth = targetMonth;

				for (WebElement h : headers) {
					String text = h.getText().trim();
					for (int m = 1; m <= 12; m++) {
						if (text.contains(monthNames[m])) {
							for (int y = 2025; y <= 2030; y++) {
								if (text.contains(String.valueOf(y))) {
									currentYear = y;
									currentMonth = m;
									break;
								}
							}
						}
					}
					break;
				}

				boolean goForward = (targetYear > currentYear)
						|| (targetYear == currentYear && targetMonth > currentMonth);

				if (goForward) {
					driver.findElement(By.cssSelector("button[aria-label='Next month']")).click();
					System.out.println("→ Next");
				} else {
					driver.findElement(By.cssSelector("button[aria-label='Previous month']")).click();
					System.out.println("← Prev");
				}
				Thread.sleep(600);

			} catch (Exception e) {
				return;
			}
		}
	}

	// ── Select guests ─────────────────────────────────────────────────────
	public void selectGuests(int adults, int children) throws InterruptedException {
		try {
			af.jsClick(guestsContainer);
			Thread.sleep(1000);
			System.out.println("Guests popup opened ✅");

// ✅ Use simpler XPath — just find increase/decrease buttons
			if (adults > 0) {
// Default is 2 adults — decrease if needed
// Increase adults
				for (int i = 0; i < adults - 2; i++) {
					driver.findElement(By.xpath("//button[@aria-label='Increase number of Adults']")).click();
					Thread.sleep(200);
				}
			}

			if (children > 0) {
				for (int i = 0; i < children; i++) {
					driver.findElement(By.xpath("//button[@aria-label='Increase number of Children']")).click();
					Thread.sleep(200);
				}
			}

// ✅ Click Done
			try {
				driver.findElement(By.cssSelector("button[data-testid='occupancy-popup-done-cta']")).click();
			} catch (Exception e) {
// ✅ Try alternative Done button
				driver.findElement(By.xpath("//button[contains(.,'Done')]")).click();
			}
			System.out.println("Guests — Adults: " + adults + " Children: " + children + " ✅");

		} catch (Exception e) {
			System.out.println("selectGuests: " + e.getMessage());
		}
	}

	// ── Set guest count ───────────────────────────────────────────────────
	private void setGuestCount(String type, int target) throws InterruptedException {
		try {
			// ✅ Get current count
			WebElement countEl = driver.findElement(By.xpath("//div[@data-testid='occupancy-popup']"
					+ "//span[contains(text(),'" + type + "')]" + "/following-sibling::div"
					+ "//span[@data-testid='occupancy-popup-" + type.toLowerCase() + "-value']"));

			int current = Integer.parseInt(countEl.getText().trim());

			System.out.println(type + " — current: " + current + " → target: " + target);

			// ✅ Increase if needed
			if (target > current) {
				for (int i = 0; i < target - current; i++) {
					driver.findElement(By.xpath("//button[@aria-label='Increase number of " + type + "']")).click();
					Thread.sleep(300);
				}
			}
			// ✅ Decrease if needed
			else if (target < current) {
				for (int i = 0; i < current - target; i++) {
					driver.findElement(By.xpath("//button[@aria-label='Decrease number of " + type + "']")).click();
					Thread.sleep(300);
				}
			}

		} catch (Exception e) {
			System.out.println("setGuestCount " + type + ": " + e.getMessage());
		}
	}

	// ── Click search ──────────────────────────────────────────────────────
	public void clickSearch() {
		searchBtn.click();
		System.out.println("Search clicked ✅");
	}

	// ── Assertions ────────────────────────────────────────────────────────
	public boolean isResultsPageDisplayed() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("searchresults"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidationErrorDisplayed() {
		try {
			Thread.sleep(2000);
			String url = driver.getCurrentUrl();
			return !url.contains("searchresults");
		} catch (Exception e) {
			return false;
		}
	}

	// ✅ Used by SC02
	public boolean staysIsResultsPageDisplayed() {
		return isResultsPageDisplayed();
	}
}