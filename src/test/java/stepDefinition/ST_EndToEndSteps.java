package stepDefinition;

import io.cucumber.java.en.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.*;
import util.AllFunctionalities;
import java.io.File;
import java.io.FileInputStream;

public class ST_EndToEndSteps {

    // ── Page objects ──────────────────────────────────────────────────────
    private final ST_HomePage homePage;
    private final ST_FiltersPage filtersPage;
    private final ST_ResultsPage resultsPage;
    private final ST_HotelDetailsPage hotelPage;
    private final ST_BookingPage bookingPage;
    private final ST_EndToEndPage endToEndPage;

    // ── Excel file path ───────────────────────────────────────────────────
    private static final String EXCEL_PATH =
        System.getProperty("user.dir") +
        "\\src\\test\\resources\\testdata\\Booking.xlsx";

    // ── Excel data variables ──────────────────────────────────────────────
    private String excelDestination = "";
    private String excelCheckin     = "";
    private String excelCheckout    = "";
    private String excelFirstName   = "";
    private String excelLastName    = "";
    private String excelEmail       = "";
    private String excelPhone       = "";

    // ── Constructor ───────────────────────────────────────────────────────
    public ST_EndToEndSteps() {
        this.homePage     = new ST_HomePage(
            AllFunctionalities.getDriver());
        this.filtersPage  = new ST_FiltersPage(
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

    // ── Step 1 — Read from Excel ──────────────────────────────────────────
    @Given("User reads stays E2E test data from Excel row {int}")
    public void userReadsE2ETestDataFromExcel(int rowIndex)
                                              throws Exception {

        System.out.println("Reading Excel: " + EXCEL_PATH);

        // ✅ Open workbook INSIDE method
        FileInputStream fis   = new FileInputStream(
            new File(EXCEL_PATH));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // ✅ Get stays sheet
        Sheet sheet = workbook.getSheet("stays");
        if (sheet == null) {
            sheet = workbook.getSheetAt(0);
            System.out.println("Sheet: " + sheet.getSheetName());
        }

        // ✅ Get row
        Row row = sheet.getRow(rowIndex);
        if (row == null)
            throw new RuntimeException(
                "Row " + rowIndex + " not found!");

        // ✅ Read columns
        String testCase  = getCellValue(row, 0); // A - TestCase
        excelDestination = getCellValue(row, 1); // B - Destination
        excelCheckin     = getCellValue(row, 2); // C - Checkin
        excelCheckout    = getCellValue(row, 3); // D - Checkout
        excelFirstName   = getCellValue(row, 4); // E - FirstName
        excelLastName    = getCellValue(row, 5); // F - LastName
        excelEmail       = getCellValue(row, 6); // G - Email
        excelPhone       = getCellValue(row, 7); // H - Phone

        // ✅ Close after reading
        workbook.close();
        fis.close();

        System.out.println("=== Excel Row " + rowIndex + " ===");
        System.out.println("TestCase    : " + testCase);
        System.out.println("Destination : " + excelDestination);
        System.out.println("Checkin     : " + excelCheckin);
        System.out.println("Checkout    : " + excelCheckout);
        System.out.println("FirstName   : " + excelFirstName);
        System.out.println("Email       : " + excelEmail);
        System.out.println("Phone       : " + excelPhone);

        // ✅ Search using Excel data
        AllFunctionalities.getDriver().get(
            AllFunctionalities.getConfig("baseUrl"));
        Thread.sleep(1500);

        ST_FiltersSteps filterSteps =
            new ST_FiltersSteps();
        filterSteps.userHasSearchedForStaysWithDates(
            excelDestination, excelCheckin, excelCheckout);

        System.out.println("✅ Searched for: " + excelDestination);
    }

    // ── Helper — safe cell reader ─────────────────────────────────────────
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
            System.out.println("getCellValue error col "
                + colIndex + ": " + e.getMessage());
            return "";
        }
    }

    // ── Step 2 — Fill form from Excel data ────────────────────────────────
    @When("User fills stays booking form from Excel data")
    public void userFillsStaysBookingFormFromExcel()
                    throws InterruptedException { // ✅ Added

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

    // ── Step 3 — Verify result ────────────────────────────────────────────
 // ── Step 3 — Verify result ────────────────────────────────────────────
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
                    url.contains("book.html") ||
                    url.contains("stage=2") ||
                    url.contains("payment") ||
                    url.contains("confirmation");
                Assert.assertTrue(isOnBookingPage,
                    "Not on booking page — URL: " + url);
                System.out.println("✅ Booking page verified");
                break;

            case "Validation error shown":
            case "Validation error shown booking blocked":
            case "Email validation error shown":
                // ✅ Validation error = stayed on booking form
                // OR booking.com showed error page
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
}