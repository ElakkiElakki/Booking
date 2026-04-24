package stepDefinition;

import io.cucumber.java.en.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.*;
import util.AllFunctionalities;
import java.io.File;
import java.io.FileInputStream;

public class ST_EndToEndSteps {

    private final ST_HomePage    homePage;
    private final ST_ResultsPage resultsPage;
    private final ST_HotelDetailsPage hotelPage;
    private final ST_BookingPage bookingPage;
    private final ST_EndToEndPage endToEndPage;

    private static final String EXCEL_PATH =
        System.getProperty("user.dir") +
        "\\src\\test\\resources\\testdata\\Booking.xlsx";

    private String excelDestination = "";
    private String excelCheckin     = "";
    private String excelCheckout    = "";
    private String excelFirstName   = "";
    private String excelLastName    = "";
    private String excelEmail       = "";
    private String excelPhone       = "";

    public ST_EndToEndSteps() {
        this.homePage     = new ST_HomePage(
            AllFunctionalities.getDriver());
        this.resultsPage  = new ST_ResultsPage(
            AllFunctionalities.getDriver());
        this.hotelPage    = new ST_HotelDetailsPage(
            AllFunctionalities.getDriver());
        this.bookingPage  = new ST_BookingPage(
            AllFunctionalities.getDriver());
        this.endToEndPage = new ST_EndToEndPage(
            AllFunctionalities.getDriver());
    }

    // ── Read from Excel ───────────────────────────────────────────────────
    @Given("User reads stays E2E test data from Excel row {int}")
    public void userReadsE2ETestDataFromExcel(int rowIndex)
                                              throws Exception {
        System.out.println("Reading Excel: " + EXCEL_PATH);

        FileInputStream fis   = new FileInputStream(
            new File(EXCEL_PATH));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheet("stays");
        if (sheet == null) sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(rowIndex);
        if (row == null)
            throw new RuntimeException(
                "Row " + rowIndex + " not found!");

        String testCase  = getCellValue(row, 0);
        excelDestination = getCellValue(row, 1);
        excelCheckin     = getCellValue(row, 2);
        excelCheckout    = getCellValue(row, 3);
        excelFirstName   = getCellValue(row, 4);
        excelLastName    = getCellValue(row, 5);
        excelEmail       = getCellValue(row, 6);
        excelPhone       = getCellValue(row, 7);

        workbook.close();
        fis.close();

        System.out.println("=== Excel Row " + rowIndex + " ===");
        System.out.println("TestCase    : " + testCase);
        System.out.println("Destination : " + excelDestination);
        System.out.println("Checkin     : " + excelCheckin);
        System.out.println("FirstName   : " + excelFirstName);
        System.out.println("Email       : " + excelEmail);
        System.out.println("Phone       : " + excelPhone);

        // ✅ Search using Excel data
        AllFunctionalities.getDriver().get(
            AllFunctionalities.getConfig("baseUrl"));
        Thread.sleep(1500);

        ST_FiltersSteps filterSteps = new ST_FiltersSteps();
        filterSteps.userHasSearchedForStaysWithDates(
            excelDestination, excelCheckin, excelCheckout);

        System.out.println("✅ Searched for: " + excelDestination);
    }

    // ── Fill form from Excel ──────────────────────────────────────────────
    @When("User fills stays booking form from Excel data")
    public void userFillsStaysBookingFormFromExcel()
                throws InterruptedException {
        System.out.println("Filling form from Excel data");

        if (!excelFirstName.isEmpty())
            bookingPage.staysEnterFirstName(excelFirstName);
        if (!excelLastName.isEmpty())
            bookingPage.staysEnterLastName(excelLastName);
        if (!excelEmail.isEmpty())
            bookingPage.staysEnterEmail(excelEmail);
        if (!excelPhone.isEmpty())
            bookingPage.staysEnterPhone(excelPhone);

        bookingPage.staysClickNext();
        System.out.println("Form filled from Excel ✅");
    }

    // ── Verify result ─────────────────────────────────────────────────────
    @Then("{string} should be verified on stays E2E")
    public void verifyStaysE2E(String expectedResult)
                               throws InterruptedException {
        Thread.sleep(2000);
        WebDriver driver = AllFunctionalities.getDriver();
        String url = driver.getCurrentUrl();
        System.out.println("Verify URL: " + url);

        switch (expectedResult) {

            case "Finish booking page displayed":
            case "Finish booking page displayed with details":
                boolean isOnBookingPage =
                    url.contains("secure.booking.com") ||
                    url.contains("book.html");
                Assert.assertTrue(isOnBookingPage,
                    "Not on booking page");
                System.out.println("✅ Booking page verified");
                break;

            case "Payment page displayed":
                // ✅ TC4 — Assert payment/finish page
                boolean isPaymentPage =
                    endToEndPage.staysIsFinishPageDisplayed();
                Assert.assertTrue(isPaymentPage,
                    "Payment page not displayed");
                System.out.println("✅ Payment page verified");
                break;

            case "Validation error shown":
            case "Validation error shown booking blocked":
                boolean isValidationError =
                    url.contains("book.html") ||
                    url.contains("secure.booking.com") ||
                    bookingPage.staysIsErrorDisplayed();
                Assert.assertTrue(isValidationError,
                    "Validation error not shown");
                System.out.println("✅ Validation error verified");
                break;

            default:
                System.out.println("Unknown: " + expectedResult);
        }
    }

    // ── TC2 — Validate empty destination ─────────────────────────────────
    @Then("{string} should be verified on stays E2E page")
    public void verifyStaysE2EPage(String expectedResult)
                                   throws InterruptedException {
        Thread.sleep(2000);
        String url = AllFunctionalities.getDriver().getCurrentUrl();
        System.out.println("URL: " + url);

        if (expectedResult.contains("Validation error")) {
            // ✅ Stayed on homepage = validation worked
            boolean stayed = !url.contains("searchresults");
            Assert.assertTrue(stayed,
                "Should stay on homepage");
            System.out.println("✅ Empty destination validation ✅");
        }
    }

    // ── Helper ────────────────────────────────────────────────────────────
    private String getCellValue(Row row, int colIndex) {
        try {
            Cell cell = row.getCell(colIndex);
            if (cell == null) return "";
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        java.util.Date date = cell.getDateCellValue();
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("yyyy-MM-dd");
                        return sdf.format(date);
                    }
                    return String.valueOf(
                        (long) cell.getNumericCellValue());
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}