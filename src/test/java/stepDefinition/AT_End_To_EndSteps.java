package stepDefinition;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.AT_End_To_End;
import util.AllFunctionalities;

public class AT_End_To_EndSteps {

    public static String currentTestCaseId;
    private AT_End_To_End galleryImagePage;

    private static final String SHEET_NAME = "attractions";

    @Given("user starts gallery image test case {string} on attractions page")
    public void startGalleryImageTestCase(String testCaseId) {
        currentTestCaseId = testCaseId;
        System.out.println("Executing Test Case: " + currentTestCaseId);

        galleryImagePage = new AT_End_To_End(AllFunctionalities.getDriver());
        galleryImagePage.launchAttractionsForGalleryImage();
    }

    @When("user searches gallery image destination {string}")
    public void searchDestinationForGalleryImage(String destination) {
        String excelDestination = getExcelValue(currentTestCaseId, 1);
        galleryImagePage.searchDestinationForGalleryImage(excelDestination);
    }

    @When("user opens first attraction details page for gallery image")
    public void openFirstDetailsPageForGalleryImage() {
        galleryImagePage.openFirstDetailsPageForGalleryImage();
    }

    @When("user clicks gallery image in attraction details page")
    public void clickGalleryImageInDetailsPage() {
        galleryImagePage.clickGalleryImageInDetailsPage();
    }

    @Then("attraction gallery popup should be displayed")
    public void verifyGalleryPopupDisplayed() {
        Assert.assertTrue(
                galleryImagePage.isGalleryPopupDisplayed(),
                "Gallery popup was not displayed after clicking gallery image."
        );
    }

    @When("user clicks select tickets button")
    public void user_clicks_select_tickets_button() {
        galleryImagePage.clickSelectTicketsButton();
    }

    @When("user clicks final select button")
    public void user_clicks_final_select_button() {
        galleryImagePage.clickFinalSelectButton();
    }

    @When("user clicks plus svg button and clicks next button")
    public void user_clicks_plus_svg_button_and_clicks_next_button() {
        galleryImagePage.clickPlusSvgButton();
        galleryImagePage.clickNextButton();
    }

    @When("user fills all required details and clicks payment details button")
    public void user_fills_all_required_details_and_clicks_payment_details_button() {

        String firstName = getExcelValue(currentTestCaseId, 2);
        String lastName  = getExcelValue(currentTestCaseId, 3);
        String email     = getExcelValue(currentTestCaseId, 4);
        String phone     = getExcelValue(currentTestCaseId, 5);

        System.out.println("Using Excel Data: "
                + firstName + " | "
                + lastName + " | "
                + email + " | "
                + phone);

        galleryImagePage.fillDetailsAndClickPaymentButton(
                firstName,
                lastName,
                email,
                phone
        );
    }

    private String getExcelValue(String testCaseId, int columnIndex) {

        Object[][] data = AllFunctionalities.getData(SHEET_NAME);

        for (Object[] row : data) {
            if (row[0].toString().trim().equalsIgnoreCase(testCaseId)) {
                return row[columnIndex] == null ? "" : row[columnIndex].toString().trim();
            }
        }

        throw new RuntimeException("TestCaseId not found in Excel: " + testCaseId);
    }
}