package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AT_GalleryPage;
import util.AllFunctionalities;

public class SC03_AT_GallerySteps {

    public static String currentTestCaseId;
    private AT_GalleryPage galleryPage;

    @Given("user starts gallery test case {string} on attractions page")
    public void user_starts_gallery_test_case_on_attractions_page(String testCaseId) {
        currentTestCaseId = testCaseId;
        System.out.println("Executing Test Case: " + currentTestCaseId);

        galleryPage = new AT_GalleryPage(AllFunctionalities.getDriver());
        galleryPage.openAttractionsPage();
    }

    @When("user searches attraction destination {string}")
    public void user_searches_attraction_destination(String destination) {
        galleryPage.searchDestination(destination);
    }

    @When("user clicks first see availability button from search results")
    public void user_clicks_first_see_availability_button_from_search_results() {
        galleryPage.clickFirstSeeAvailabilityFromResults();
    }

    @Then("attraction gallery should be visible")
    public void attraction_gallery_should_be_visible() {
        Assert.assertTrue(
                galleryPage.isGalleryVisible(),
                "Attraction gallery was not visible after clicking See availability."
        );
    }
}
