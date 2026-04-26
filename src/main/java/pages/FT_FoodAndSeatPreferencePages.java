package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class FT_FoodAndSeatPreferencePages {

    WebDriver driver;
    WebDriverWait wait;

    public FT_FoodAndSeatPreferencePages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By meal1 = By.id("meal_choice_1");
    By meal2 = By.id("meal_choice_2");

    By adultDropdown = By.xpath("//div[@data-testid='upb_title-flight_adult']");
    By childDropdown = By.xpath("//div[@data-testid='upb_title-flight_child']");

    By nextBtn = By.xpath("//button[.//span[text()='Next'] or contains(.,'Next')]");
    By flexibleOption = By.xpath("//input[@name='ticket-type' and @value='flexible']");

    By checkAndPay = By.xpath(
            "//button[.//span[contains(text(),'Check and pay')]]" +
            " | //button[contains(.,'Check and pay')]" +
            " | //button[contains(.,'Continue')]"
    );

    By skip = By.xpath(
            "//button[.//span[contains(text(),'Skip')]]" +
            " | //button[contains(.,'Skip')]"
    );

    private void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    private void scrollCenter(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
    }

    private void waitForUI() {
        try {
            Thread.sleep(1000);
        } catch (Exception ignored) {
        }
    }

    public void selectFood() {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(meal1)))
                .selectByVisibleText("Kosher · Free");

        new Select(wait.until(ExpectedConditions.elementToBeClickable(meal2)))
                .selectByVisibleText("Vegetarian · Free");

        System.out.println("Food selected");
    }

    public void expandPriceDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(adultDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(childDropdown)).click();
    }

    public void waitForFlexPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nextBtn));
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
            scrollCenter(option);
            jsClick(option);

            System.out.println("Clicked Travel protection radio card");

        } catch (Exception e) {
            System.out.println("Travel protection not clickable. Skipping.");
        }
    }

    public void selectFlexibleOption() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(flexibleOption));
        jsClick(el);
        System.out.println("Flexible ticket selected");
    }

    public void clickNext() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
        scrollCenter(btn);
        jsClick(btn);
        System.out.println("Clicked Next");
    }

    public void selectSeatsForBothSegments() {
        System.out.println("Selecting seats for both segments");

        clickTwoSeats();

        By secondTab = By.xpath("(//button[contains(@data-testid,'seat_map_tab')])[2]");

        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(secondTab));
            jsClick(tab);
            System.out.println("Switched to second flight tab");
            waitForUI();
            clickTwoSeats();
        } catch (Exception e) {
            System.out.println("Second flight tab not found. Continuing.");
        }
    }

    private void clickTwoSeats() {

		By seatsLocator = By.xpath("//button[contains(@data-testid,'seat_') and @data-seat]");

		List<WebElement> seats = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(seatsLocator));

		int clicked = 0;

		for (WebElement seat : seats) {
			try {
				if (seat.isDisplayed() && seat.isEnabled()) {

					String seatNo = seat.getAttribute("data-seat");

					// 🔥 avoid clicking same seat again
					if (seat.getAttribute("class").toLowerCase().contains("selected")) {
						continue;
					}

					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", seat);

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", seat);

					System.out.println("Selected seat → " + seatNo);

					clicked++;
					waitForUI();

					if (clicked == 2)
						break;
				}

			} catch (Exception e) {
				// ignore and try next
			}
		}

		if (clicked < 2) {
			throw new RuntimeException("❌ Could not select two seats");
		}
	}
    public void clickCheckOrSkip() {
        try {
            WebElement checkBtn = wait.until(ExpectedConditions.elementToBeClickable(checkAndPay));
            scrollCenter(checkBtn);
            jsClick(checkBtn);
            System.out.println("Clicked Check and Pay / Continue");
            return;
        } catch (Exception ignored) {
        }

        try {
            WebElement skipBtn = wait.until(ExpectedConditions.elementToBeClickable(skip));
            scrollCenter(skipBtn);
            jsClick(skipBtn);
            System.out.println("Clicked Skip");
        } catch (Exception e) {
            throw new RuntimeException("Neither Check and Pay nor Skip button found.");
        }
    }

    public void verifyPaymentPage() {
        wait.until(driver ->
                driver.getCurrentUrl().toLowerCase().contains("payment")
                        || driver.getPageSource().toLowerCase().contains("payment")
                        || driver.getPageSource().toLowerCase().contains("card number")
        );

        System.out.println("Payment page reached");
    }
}