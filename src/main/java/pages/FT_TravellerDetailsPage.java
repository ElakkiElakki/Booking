package pages;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;

import util.AllFunctionalities;

public class FT_TravellerDetailsPage {

	WebDriver driver;
	AllFunctionalities util;
	Object[][] data;

	public FT_TravellerDetailsPage(WebDriver driver) {
		this.driver = driver;
		this.util = new AllFunctionalities(driver);
		data = loadExcelData1();
	}

	// 🔹 SAFE DATA (handles null + 2018.0 issue)
	private String getData(int row, int col) {
		if (data[row][col] == null)
			return "";

		String val = data[row][col].toString();

		if (val.endsWith(".0")) {
			val = val.substring(0, val.length() - 2);
		}

		return val;
	}

	// 🔹 STRONG CLEAR (no utility change)
	private void clearAndType(By locator, String value) {
		WebElement el = util.waitForVisible(locator);

		el.sendKeys(Keys.CONTROL + "a");
		el.sendKeys(Keys.DELETE);
		el.sendKeys(value);
	}

	private Object[][] loadExcelData1() {
		try {
			FileInputStream fis = new FileInputStream("src\\test\\resources\\testdata\\Booking.xlsx");

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet("flights");

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getPhysicalNumberOfCells();

			Object[][] data = new Object[rows - 1][cols];

			for (int i = 1; i < rows; i++) {

				Row row = sheet.getRow(i);

				for (int j = 0; j < cols; j++) {

					if (row == null || row.getCell(j) == null) {
						data[i - 1][j] = "";
					} else {
						data[i - 1][j] = row.getCell(j).toString();
					}
				}
			}

			wb.close();
			fis.close();

			return data;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 🔹 LOCATORS
	By adultAddBtn = By.xpath("(//button[contains(@aria-label,'Add this traveler')])[1]");
	By correctAdultBtn = By.xpath("(//button[contains(@aria-label,'Edit this traveler')])[1]");
	By childAddBtn = By.xpath("//button[contains(@aria-label,\"Add this traveler’s details\")]");
	By firstName = By.xpath("//input[contains(@name,'firstName')]");
	By lastName = By.xpath("//input[contains(@name,'lastName')]");
	By gender = By.xpath("//select[contains(@name,'gender')]");
	By doneBtn = By.xpath("//button[.//span[text()='Done']]");

	By email = By.name("booker.email");
	By phone = By.name("number");
	By nextBtn = By.xpath("//button[.//span[text()='Next']]");

	By errorMsg = By.xpath("//*[contains(text(),'Enter') or contains(text(),'valid') or contains(@class,'error')]");

	// ================= ADULT FLOW =================

	public void enterAdultFullFlow() {

		// STEP 1 → INVALID (empty last name)
		util.click(util.waitForVisible(adultAddBtn));

		clearAndType(firstName, getData(0, 0));
		clearAndType(lastName, "");

		util.selectByVisibleText(util.waitForVisible(gender), getData(0, 2));
		util.jsClick(util.waitForVisible(doneBtn));

		verifyTravellerError();

		// STEP 2 → INVALID (special char)
		util.click(util.waitForVisible(correctAdultBtn));

		clearAndType(firstName, getData(1, 0));
		clearAndType(lastName, getData(1, 1));

		util.selectByVisibleText(util.waitForVisible(gender), getData(1, 2));
		util.jsClick(util.waitForVisible(doneBtn));

		verifyTravellerError();

		// STEP 3 → VALID
		util.click(util.waitForVisible(correctAdultBtn));

		clearAndType(firstName, getData(2, 0));
		clearAndType(lastName, getData(2, 1));

		util.selectByVisibleText(util.waitForVisible(gender), getData(2, 2));
		util.jsClick(util.waitForVisible(doneBtn));
	}

	// ================= CHILD =================

	public void enterValidChild() {

		// 🔥 wait properly before clicking
		util.hardWait(2);

		util.click(util.waitForVisible(childAddBtn));

		By childFirst = By.name("passengers.1.firstName");
		By childLast = By.name("passengers.1.lastName");
		By childGender = By.name("passengers.1.gender");
		By childMonth = By.name("passengers.1.birthDate__month");
		By childDay = By.name("passengers.1.birthDate__day");
		By childYear = By.name("passengers.1.birthDate__year");

		clearAndType(childFirst, getData(3, 0));
		clearAndType(childLast, getData(3, 1));

		util.selectByVisibleText(util.waitForVisible(childGender), getData(3, 2));
		util.selectByVisibleText(util.waitForVisible(childMonth), getData(3, 3));

		clearAndType(childDay, getData(3, 4));
		clearAndType(childYear, getData(3, 5));

		util.jsClick(util.waitForVisible(doneBtn));
	}
	// ================= EMAIL FLOW =================

	public void enterEmailFlow() {

		// STEP 1 → INVALID
		clearAndType(email, "abc@.com");

		verifyEmailError();

		// STEP 2 → VALID
		clearAndType(email, AllFunctionalities.getConfig("email"));

		// 🔥 trigger validation (VERY IMPORTANT)
		util.click(util.waitForVisible(nextBtn));

		// 🔥 small wait for UI to update
		util.hardWait(2);
	}

	public void enterPhone() {
		clearAndType(phone, "9876543210");
	}

	public void clickNext() {
		util.jsClick(util.waitForVisible(nextBtn));
	}

	// ================= COMMON =================

	public void verifyTravellerError() {
		if (util.getElements(errorMsg).size() == 0) {
			throw new AssertionError("No traveller error shown");
		}
	}

	public void verifyEmailError() {
		if (util.getElements(errorMsg).size() == 0) {
			throw new AssertionError("No email error shown");
		}
	}
}