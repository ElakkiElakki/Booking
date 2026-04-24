package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AT_FavPage;
import util.AllFunctionalities;

public class SC04_AT_FavSteps {

    public static String currentTestCaseId;
    private AT_FavPage favPage;

    @Given("user starts fav test case {string} on attractions page")
    public void user_starts_fav_test_case_on_attractions_page(String testCaseId) {
        currentTestCaseId = testCaseId;
        System.out.println("Executing Test Case: " + currentTestCaseId);

        favPage = new AT_FavPage(AllFunctionalities.getDriver());
        favPage.openAttractionsPage();
    }

    @When("user searches fav attraction destination {string}")
    public void user_searches_fav_attraction_destination(String destination) {
        favPage.searchDestination(destination);
    }

    @When("user selects the heart icon from attraction search result page")
    public void user_selects_the_heart_icon_from_attraction_search_result_page() {
        favPage.selectHeartIconInResultPage();
    }

    @When("user opens my fav from profile menu")
    public void user_opens_my_fav_from_profile_menu() {
        favPage.openMyFavFromProfileMenu();
    }

    @Then("selected attraction should be displayed in my fav page")
    public void selected_attraction_should_be_displayed_in_my_fav_page() {
        Assert.assertTrue(
                favPage.isSelectedAttractionDisplayedInFavPage(),
                "Selected attraction is not displayed in Saved page: " + favPage.getAttractionName()
        );
    }

    @When("user goes back to attraction search results page")
    public void user_goes_back_to_attraction_search_results_page() {
        favPage.goBackToSearchResultsPage();
    }

    @When("user deselects the same heart icon from attraction search result page")
    public void user_deselects_the_same_heart_icon_from_attraction_search_result_page() {
        favPage.deselectSameHeartIconInResultPage();
    }

    @Then("selected attraction should not be displayed in my fav page")
    public void selected_attraction_should_not_be_displayed_in_my_fav_page() {
        Assert.assertTrue(
                favPage.isSelectedAttractionNotDisplayedInFavPage(),
                "Deselected attraction is still displayed in Saved page: " + favPage.getAttractionName()
        );
    }
}